package com.darrenthiores.core.data.local.starting

import efifalitecore.playerdb.StartingEntity
import kotlinx.coroutines.flow.Flow

interface StartingDataSource {

    suspend fun insertStarting(starting: StartingEntity)

    fun getStarting(): Flow<List<StartingEntity>>

    fun getStartingById(id: Int): Flow<StartingEntity>

    suspend fun checkStartingExist(playerId: Int): Boolean

    suspend fun updateUserStarting(player: StartingEntity)

    suspend fun deleteUserStarting(player: StartingEntity)
}