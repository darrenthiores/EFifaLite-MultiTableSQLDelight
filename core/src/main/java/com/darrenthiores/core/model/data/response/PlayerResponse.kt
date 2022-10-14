package com.darrenthiores.core.model.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponse(
    val player: PlayerDataResponse,
    val statistics: List<StatisticResponse>
)
