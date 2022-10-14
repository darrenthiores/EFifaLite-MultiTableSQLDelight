package com.darrenthiores.core.domain

import androidx.paging.PagingData
import com.darrenthiores.core.model.domain.PlayerDomain
import com.darrenthiores.core.model.domain.StartingDomain
import com.darrenthiores.core.model.domain.UserPlayerDomain
import kotlinx.coroutines.flow.Flow

interface FootballUseCase {
    // data store
    fun getCoin(): Flow<Int>

    suspend fun updateCoin(coinUsed: Int)

    fun getLogin(): Flow<Boolean>

    suspend fun updateLogin(state: Boolean)

    fun getFormation(): Flow<Int>

    suspend fun updateFormation(formation: Int)

    // network bound
    fun getPlayers(league: Int): Flow<PagingData<PlayerDomain>>

    // draw
    suspend fun draw(league: Int): PlayerDomain

    // switch starting
    suspend fun updateStarting(player: UserPlayerDomain, starting: StartingDomain, place: Int)

    // local db

    fun getUserPlayers(): Flow<List<UserPlayerDomain>>

    fun getPlayersByPosition(position: String): Flow<List<UserPlayerDomain>>

    suspend fun updateUserPlayer(player: UserPlayerDomain)

    suspend fun deleteUserPlayer(player: UserPlayerDomain)

    fun getStarting(): Flow<List<StartingDomain>>

    fun getStartingById(id: Int): Flow<StartingDomain>

    suspend fun checkStartingExist(playerId: Int): Boolean

    suspend fun updateUserStarting(player: StartingDomain)
}