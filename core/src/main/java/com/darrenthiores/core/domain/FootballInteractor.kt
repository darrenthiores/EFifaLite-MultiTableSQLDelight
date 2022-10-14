package com.darrenthiores.core.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.darrenthiores.core.data.repository.IFootballRepository
import com.darrenthiores.core.model.domain.PlayerDomain
import com.darrenthiores.core.model.domain.StartingDomain
import com.darrenthiores.core.model.domain.UserPlayerDomain
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class FootballInteractor(
    private val footballRepository: IFootballRepository
): FootballUseCase {
    override fun getCoin(): Flow<Int> = footballRepository.getCoin()

    override suspend fun updateCoin(coinUsed: Int) = footballRepository.updateCoin(coinUsed)

    override fun getLogin(): Flow<Boolean> = footballRepository.getLogin()

    override suspend fun updateLogin(state: Boolean) = footballRepository.updateLogin(state)

    override fun getFormation(): Flow<Int> = footballRepository.getFormation()

    override suspend fun updateFormation(formation: Int) = footballRepository.updateFormation(formation)

    override fun getPlayers(league: Int): Flow<PagingData<PlayerDomain>> =
        footballRepository.getPlayers(league)

    override suspend fun draw(league: Int): PlayerDomain = footballRepository.draw(league)

    override suspend fun updateStarting(
        player: UserPlayerDomain,
        starting: StartingDomain,
        place: Int
    ) {
        footballRepository.updateStarting(player, starting, place)
    }

    override fun getUserPlayers(): Flow<List<UserPlayerDomain>> = footballRepository.getUserPlayers()

    override fun getPlayersByPosition(position: String): Flow<List<UserPlayerDomain>> =
        footballRepository.getPlayersByPosition(position)

    override suspend fun updateUserPlayer(player: UserPlayerDomain) = footballRepository.updateUserPlayer(player)

    override suspend fun deleteUserPlayer(player: UserPlayerDomain) = footballRepository.deleteUserPlayer(player)

    // override suspend fun insertStarting(starting: UserPlayerDomain, place: Int) = footballRepository.insertStarting(starting, place)

    override fun getStarting(): Flow<List<StartingDomain>> = footballRepository.getStarting()

    override fun getStartingById(id: Int): Flow<StartingDomain> = footballRepository.getStartingById(id)

    override suspend fun checkStartingExist(playerId: Int): Boolean =
        footballRepository.checkStartingExist(playerId)

    override suspend fun updateUserStarting(player: StartingDomain) = footballRepository.updateUserStarting(player)

    // override suspend fun deleteUserStarting(player: StartingDomain) = footballRepository.deleteUserStarting(player)
}