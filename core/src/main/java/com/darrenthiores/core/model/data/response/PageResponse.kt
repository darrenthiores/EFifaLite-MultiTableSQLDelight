package com.darrenthiores.core.model.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PageResponse(
    val paging: PagingResponse
)

@Serializable
data class PagingResponse(
    val total: Int
)