package com.darrenthiores.core.data.local.player

import androidx.paging.PagingSource
import com.darrenthiores.core.PlayerDatabase
import com.squareup.sqldelight.android.paging3.QueryPagingSource
import efifalitecore.playerdb.PlayerEntity
import efifalitecore.playerdb.PlayerEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerDataSourceImpl(
    db: PlayerDatabase
): PlayerDataSource {
    private val playerQueries = db.playerEntityQueries

    override fun getQueries(): PlayerEntityQueries = playerQueries

    override fun insert(players: List<PlayerEntity>) =
        playerQueries.transaction {
            players.forEach { player ->
                playerQueries.insert(
                    remoteId = null,
                    id = player.id,
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
                    rating = player.rating
                )
            }
        }

    override fun getPlayers(league: Int): PagingSource<Long, PlayerEntity> =
        QueryPagingSource(
            countQuery = playerQueries.count(),
            transacter = playerQueries,
            dispatcher = Dispatchers.IO,
            queryProvider = { limit, offset -> playerQueries.getPlayers(league, limit, offset) }
        )

    override fun clearAll() =
        playerQueries.clearAll()
}