package com.heyfood.routing.requests

data class PaginationRequest (
    val take: Int = 50,
    val skip: Int = 0
)