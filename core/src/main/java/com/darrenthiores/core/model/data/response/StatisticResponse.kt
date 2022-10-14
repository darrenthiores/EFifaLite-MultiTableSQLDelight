package com.darrenthiores.core.model.data.response

import kotlinx.serialization.Serializable

@Serializable
data class StatisticResponse(
    val team: TeamResponse,
    val league: LeagueResponse,
    val games: GamesResponse
)

@Serializable
data class TeamResponse(
    val name: String?,
    val logo: String?
)

@Serializable
data class LeagueResponse(
    val id: Int
)

@Serializable
data class GamesResponse(
    val position: String?,
    val rating: String?
)