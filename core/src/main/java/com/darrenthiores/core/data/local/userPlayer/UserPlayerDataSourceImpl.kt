package com.darrenthiores.core.data.local.userPlayer

import com.darrenthiores.core.PlayerDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import efifalitecore.playerdb.UserPlayerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserPlayerDataSourceImpl(
    db: PlayerDatabase
): UserPlayerDataSource {
    private val userPlayerQueries = db.userPlayerEntityQueries

    override suspend fun insertUserPlayer(player: UserPlayerEntity) {
        withContext(Dispatchers.Default) {
            userPlayerQueries.insertPlayer(
                id = null,
                playerId = player.id,
                name = player.name,
                age = player.age,
                nationality = player.nationality,
                height = player.height,
                weight = player.weight,
                photo = player.photo,
                club = player.club,
                clubPhoto = player.clubPhoto,
                league = player.league,
                position = player.position,
                rating = player.rating,
                level = player.level
            )
        }
    }

    override fun getUserPlayers(): Flow<List<UserPlayerEntity>> =
        userPlayerQueries
            .getUserPlayers()
            .asFlow()
            .mapToList()

    override fun getPlayersByPosition(position: String): Flow<List<UserPlayerEntity>> =
        userPlayerQueries
            .getPlayersByPosition(position)
            .asFlow()
            .mapToList()

    override suspend fun updateUserPlayer(player: UserPlayerEntity) {
        withContext(Dispatchers.Default) {
            userPlayerQueries.updatePlayer(
                id = player.id,
                playerId = player.playerId,
                name = player.name,
                age = player.age,
                nationality = player.nationality,
                height = player.height,
                weight = player.weight,
                photo = player.photo,
                club = player.club,
                clubPhoto = player.clubPhoto,
                league = player.league,
                position = player.position,
                rating = player.rating,
                level = player.level
            )
        }
    }

    override suspend fun deleteUserPlayer(player: UserPlayerEntity) {
        withContext(Dispatchers.Default) {
            userPlayerQueries.deleteUserPlayer(
                id = player.id
            )
        }
    }
}