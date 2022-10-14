package com.darrenthiores.core.data.repository

import androidx.paging.*
import com.darrenthiores.core.data.dataStore.DataStore
import com.darrenthiores.core.data.local.LocalDataSource
import com.darrenthiores.core.data.mediator.FootballRemoteMediator
import com.darrenthiores.core.data.remote.source.RemoteDataSource
import com.darrenthiores.core.model.domain.PlayerDomain
import com.darrenthiores.core.model.domain.StartingDomain
import com.darrenthiores.core.model.domain.UserPlayerDomain
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import kotlin.random.Random

private const val LOAD_SIZE = 20

@ExperimentalPagingApi
class FootballRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStore: DataStore
): IFootballRepository {
    override fun getCoin(): Flow<Int> = dataStore.coin

    override suspend fun updateCoin(coinUsed: Int) = dataStore.updateCoin(coinUsed)

    override fun getLogin(): Flow<Boolean> = dataStore.login

    override suspend fun updateLogin(state: Boolean) = dataStore.updateLogin(state)

    override fun getFormation(): Flow<Int> = dataStore.formation

    override suspend fun updateFormation(formation: Int) = dataStore.updateFormation(formation)

    override fun getPlayers(league: Int): Flow<PagingData<PlayerDomain>> {
        val pagingSourceFactory = { localDataSource.getPlayers(league) }
        return Pager(
            config = PagingConfig(pageSize = LOAD_SIZE, initialLoadSize = 60, enablePlaceholders = true),
            remoteMediator = FootballRemoteMediator(
                league, localDataSource, remoteDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                DataMapper.mapPlayerEntityToDomain(it)
            }
        }
    }

    override suspend fun draw(league: Int): PlayerDomain {
        Timber.d("Draw: $league")
        val num = remoteDataSource.getPage(league)
        Timber.d("Draw: $num")
        val randomNum = Random.nextLong(1, num.toLong())
        Timber.d("Draw: $randomNum")
        val player = remoteDataSource.getPlayer(league, randomNum)
        Timber.d("Draw: $player")
        val userPlayerEntity = DataMapper.mapResponseToUserPlayerEntity(player)
        localDataSource.insertUserPlayer(userPlayerEntity)

        return DataMapper.mapResponseToDomain(player)
    }

    override suspend fun updateStarting(
        player: UserPlayerDomain,
        starting: StartingDomain,
        place: Int
    ) {
        val startingEntity = DataMapper.mapUserPlayerToStartingEntity(player, place).copy(id = starting.id)
        val userPlayerEntity = DataMapper.mapStartingToUserPlayerEntity(starting).copy(id = player.id)

        localDataSource.updateUserStarting(startingEntity)
        localDataSource.updateUserPlayer(userPlayerEntity)
    }

    override fun getUserPlayers(): Flow<List<UserPlayerDomain>> =
        localDataSource.getUserPlayers().map { DataMapper.mapUserPlayerEntityToDomain(it) }

    override fun getPlayersByPosition(position: String): Flow<List<UserPlayerDomain>> =
        localDataSource.getPlayersByPosition(position).map { DataMapper.mapUserPlayerEntityToDomain(it) }

    override suspend fun updateUserPlayer(player: UserPlayerDomain) {
        val level = player.level + 1
        val rating = player.rating + 0.3
        val userPlayerEntity = DataMapper.mapUserPlayerToEntity(player).copy(level = level, rating = rating)
        localDataSource.updateUserPlayer(userPlayerEntity)
    }

    override suspend fun deleteUserPlayer(player: UserPlayerDomain) {
        val userPlayerEntity = DataMapper.mapUserPlayerToEntity(player)
        localDataSource.deleteUserPlayer(userPlayerEntity)
    }

//    override suspend fun insertStarting(starting: UserPlayerDomain, place: Int) {
//        val startingEntity = DataMapper.mapUserPlayerToStartingEntity(starting, place)
//        val userPlayerEntity = DataMapper.mapUserPlayerToEntity(starting)
//        localDataSource.insertStarting(startingEntity)
//        localDataSource.deleteUserPlayer(userPlayerEntity)
//    }

    override fun getStarting(): Flow<List<StartingDomain>> =
        localDataSource.getStarting().map { DataMapper.mapStartingEntitiesToDomain(it) }

    override fun getStartingById(id: Int): Flow<StartingDomain> =
        localDataSource.getStartingById(id).map { DataMapper.mapStartingEntityToDomain(it) }

    override suspend fun checkStartingExist(playerId: Int): Boolean =
        localDataSource.checkStartingExist(playerId)

    override suspend fun updateUserStarting(player: StartingDomain) {
        val level = player.level + 1
        val rating = player.rating + 0.3
        val startingEntity = DataMapper.mapStartingToEntity(player).copy(level = level, rating = rating)
        localDataSource.updateUserStarting(startingEntity)
    }

//    override suspend fun deleteUserStarting(player: StartingDomain) {
//        val startingEntity = DataMapper.mapStartingToEntity(player)
//        val userPlayerEntity = DataMapper.mapStartingToUserPlayerEntity(player)
//        localDataSource.deleteUserStarting(startingEntity)
//        localDataSource.insertUserPlayer(userPlayerEntity)
//    }
}