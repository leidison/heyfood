package com.heyfood.plugins

import com.heyfood.routing.requests.PaginationRequest
import io.ktor.server.application.*

fun ApplicationCall.toPaginationRequest(): PaginationRequest {
    val take = request.queryParameters["take"]?.toInt() ?: 50
    val skip = request.queryParameters["skip"]?.toInt() ?: 0

    return PaginationRequest(take, skip)
}