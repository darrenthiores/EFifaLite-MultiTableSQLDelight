package com.darrenthiores.core.data.remote.ktor

import com.darrenthiores.core.BuildConfig
import com.darrenthiores.core.model.data.response.PageResponse
import com.darrenthiores.core.model.data.response.PagingResponse
import com.darrenthiores.core.model.data.response.PlayersResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import timber.log.Timber

class FootballServiceImpl(
    private val client: HttpClient
): FootballService {

    override suspend fun getPlayers(league: Int, page: Long): PlayersResponse =
        try {
            client.get {
                url(HttpRoutes.PLAYER_URL)
                parameter("season", 2020)
                parameter("league", league)
                parameter("page", page)

                headers {
                    append("x-rapidapi-key", BuildConfig.API_KEY)
                }
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Timber.e("Error: ${e.response.status.description}")
            PlayersResponse(emptyList())
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Timber.e("Error: ${e.response.status.description}")
            PlayersResponse(emptyList())
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Timber.e("Error: ${e.response.status.description}")
            PlayersResponse(emptyList())
        } catch (e: Exception) {
            // 6xx - responses
            Timber.e("Error: ${e.message}")
            PlayersResponse(emptyList())
        }

    override suspend fun getPaging(league: Int): PageResponse =
        try {
            client.get {
                url(HttpRoutes.PLAYER_URL)
                parameter("season", 2021)
                parameter("league", league)
                parameter("page", 1)

                headers {
                    append("x-rapidapi-key", BuildConfig.API_KEY)
                }
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Timber.e("Error: ${e.response.status.description}")
            PageResponse(PagingResponse(0))
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Timber.e("Error: ${e.response.status.description}")
            PageResponse(PagingResponse(0))
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Timber.e("Error: ${e.response.status.description}")
            PageResponse(PagingResponse(0))
        } catch (e: Exception) {
            // 6xx - responses
            Timber.e("Error: ${e.message}")
            PageResponse(PagingResponse(0))
        }

}