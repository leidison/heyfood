package com.heyfood.routing.requests

data class UpdateUserRequest (
    val name: String,
    val contact: ContactRequest?
)