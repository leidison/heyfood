package com.heyfood.routing.requests

import kotlinx.serialization.Serializable

@Serializable
data class PaginationRequest (
    val take: Int = 50,
    val skip: Int = 0
)