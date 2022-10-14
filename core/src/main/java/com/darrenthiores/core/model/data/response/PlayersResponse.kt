package com.darrenthiores.core.model.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponse(
    val response: List<PlayerResponse>
)
