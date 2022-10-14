package com.darrenthiores.core.data.local

import androidx.paging.PagingSource
import com.darrenthiores.core.PlayerDatabase
import com.darrenthiores.core.data.local.player.PlayerDataSource
import com.darrenthiores.core.data.local.player.PlayerRemoteKeysDataSource
import com.darrenthiores.core.data.local.starting.StartingDataSource
import com.darrenthiores.core.data.local.userPlayer.UserPlayerDataSource
import efifalitecore.playerdb.*
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val db: PlayerDatabase,
    private val playerDataSource: PlayerDataSource,
    private val playerRemoteKeysDataSource: PlayerRemoteKeysDataSource,
    private val userPlayerDataSource: UserPlayerDataSource,
    private val startingDataSource: StartingDataSource
) {
    fun getPlayerDb(): PlayerDatabase = db

    // load data
    fun insert(players: List<PlayerEntity>) {
        playerDataSource.insert(players)
    }

    fun getPlayers(league: Int): PagingSource<Long, PlayerEntity> =
        playerDataSource.getPlayers(league)

    fun clearAll() = playerDataSource.clearAll()

    suspend fun getRemoteKeys(id: Long): PlayerRemoteKeys? = playerRemoteKeysDataSource.getRemoteKeys(id)

    fun addAllRemoteKeys(remoteKeys: List<PlayerRemoteKeys>) = playerRemoteKeysDataSource.addAllRemoteKeys(remoteKeys)

    fun deleteAllRemoteKeys() = playerRemoteKeysDataSource.deleteAllRemoteKeys()

    // user players
    suspend fun insertUserPlayer(player: UserPlayerEntity) {
        userPlayerDataSource.insertUserPlayer(player)
    }

    fun getUserPlayers(): Flow<List<UserPlayerEntity>> =
        userPlayerDataSource.getUserPlayers()

    fun getPlayersByPosition(position: String): Flow<List<UserPlayerEntity>> =
        userPlayerDataSource.getPlayersByPosition(position)

    suspend fun updateUserPlayer(player: UserPlayerEntity) {
        userPlayerDataSource.updateUserPlayer(player)
    }

    suspend fun deleteUserPlayer(player: UserPlayerEntity) {
        userPlayerDataSource.deleteUserPlayer(player)
    }

    // user starting
    suspend fun insertStarting(starting: StartingEntity) {
        startingDataSource.insertStarting(starting)
    }

    fun getStarting(): Flow<List<StartingEntity>> =
        startingDataSource.getStarting()

    fun getStartingById(playerId: Int): Flow<StartingEntity> =
        startingDataSource.getStartingById(playerId)

    suspend fun checkStartingExist(playerId: Int): Boolean =
        startingDataSource.checkStartingExist(playerId)

    suspend fun updateUserStarting(player: StartingEntity) {
        startingDataSource.updateUserStarting(player)
    }

    suspend fun deleteUserStarting(player: StartingEntity) {
        startingDataSource.deleteUserStarting(player)
    }
}