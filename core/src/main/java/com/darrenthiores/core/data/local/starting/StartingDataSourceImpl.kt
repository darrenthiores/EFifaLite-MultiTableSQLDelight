package com.darrenthiores.core.data.local.starting

import com.darrenthiores.core.PlayerDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import efifalitecore.playerdb.StartingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StartingDataSourceImpl(
    db: PlayerDatabase
): StartingDataSource {
    private val startingQueries = db.startingEntityQueries

    override suspend fun insertStarting(starting: StartingEntity) {
        withContext(Dispatchers.Default) {
            startingQueries.insertStarting(
                id = starting.id,
                playerId = starting.playerId,
                name = starting.name,
                age = starting.age,
                nationality = starting.nationality,
                height = starting.height,
                weight = starting.weight,
                photo = starting.photo,
                club = starting.club,
                clubPhoto = starting.clubPhoto,
                league = starting.league,
                position = starting.position,
                rating = starting.rating,
                level = starting.level,
                place = starting.place
            )
        }
    }

    override fun getStarting(): Flow<List<StartingEntity>> =
        startingQueries
            .getStarting()
            .asFlow()
            .mapToList()

    override fun getStartingById(id: Int): Flow<StartingEntity> =
        startingQueries
            .getStartingById(id)
            .asFlow()
            .mapToOne()

    override suspend fun checkStartingExist(playerId: Int): Boolean =
        startingQueries
            .checkStartingExist(playerId)
            .executeAsOne()

    override suspend fun updateUserStarting(player: StartingEntity) {
        withContext(Dispatchers.Default) {
            startingQueries.updateStarting(
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
                level = player.level,
                place = player.place
            )
        }
    }

    override suspend fun deleteUserStarting(player: StartingEntity) {
        withContext(Dispatchers.Default) {
            startingQueries.deleteUserStarting(player.id)
        }
    }
}