package com.darrenthiores.core.data.remote.ktor

import com.darrenthiores.core.BuildConfig

object HttpRoutes {

    private const val BASE_URL = "https://${BuildConfig.BASE_URL}"
    const val PLAYER_URL = "$BASE_URL/players"

}