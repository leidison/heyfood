package com.heyfood.routing.responses

interface PaginatedResponse<T: Any> {
    val data: List<T>
    val total: Int?
}