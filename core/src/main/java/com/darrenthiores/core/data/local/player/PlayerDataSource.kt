package com.darrenthiores.core.data.local.player

import androidx.paging.PagingSource
import efifalitecore.playerdb.PlayerEntity
import efifalitecore.playerdb.PlayerEntityQueries

interface PlayerDataSource {
    fun getQueries(): PlayerEntityQueries

    fun insert(players: List<PlayerEntity>)

    fun getPlayers(league: Int): PagingSource<Long, PlayerEntity>

    fun clearAll()
}