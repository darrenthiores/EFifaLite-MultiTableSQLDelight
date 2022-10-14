package com.darrenthiores.core.data.local.userPlayer

import efifalitecore.playerdb.UserPlayerEntity
import kotlinx.coroutines.flow.Flow

interface UserPlayerDataSource {
    suspend fun insertUserPlayer(player: UserPlayerEntity)

    fun getUserPlayers(): Flow<List<UserPlayerEntity>>

    fun getPlayersByPosition(position: String): Flow<List<UserPlayerEntity>>

    suspend fun updateUserPlayer(player: UserPlayerEntity)

    suspend fun deleteUserPlayer(player: UserPlayerEntity)
}