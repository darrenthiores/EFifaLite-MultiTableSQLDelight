package com.darrenthiores.core.data.remote.source

import com.darrenthiores.core.data.remote.ktor.FootballService
import com.darrenthiores.core.model.data.response.PlayerResponse

class RemoteDataSource(
    private val footballService: FootballService
) {

    suspend fun getPlayers(
        league: Int,
        page: Long
    ): List<PlayerResponse> = footballService.getPlayers(league, page).response

    suspend fun getPlayer(
        league: Int,
        page: Long
    ): PlayerResponse {
        val list = footballService.getPlayers(league, page).response
        return list.random()
    }

    suspend fun getPage(league: Int): Int = footballService.getPaging(league).paging.total

}