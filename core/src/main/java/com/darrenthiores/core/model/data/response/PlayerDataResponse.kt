package com.darrenthiores.core.model.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDataResponse(
    val id: Int,
    val name: String,
    val age: Int?,
    val nationality: String?,
    val height: String?,
    val weight: String?,
    val photo: String?
)
