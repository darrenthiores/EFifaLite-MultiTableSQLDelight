package com.darrenthiores.core.model.domain

data class PlayerDomain(
    val id: Int,
    val name: String,
    val age: Int,
    val nationality: String,
    val height: String,
    val weight: String,
    val photo: String,
    val club: String,
    val clubPhoto: String,
    val league: Int,
    val position: String,
    val rating: Double
)
