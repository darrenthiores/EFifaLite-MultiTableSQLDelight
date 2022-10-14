package com.darrenthiores.core.data.remote.ktor

import com.darrenthiores.core.model.data.response.PageResponse
import com.darrenthiores.core.model.data.response.PlayersResponse

interface FootballService {

    suspend fun getPlayers(league: Int, page: Long): PlayersResponse

    suspend fun getPaging(league: Int): PageResponse

}