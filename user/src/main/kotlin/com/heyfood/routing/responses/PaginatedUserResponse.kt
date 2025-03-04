package com.heyfood.routing.responses

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedUserResponse(
    override var data: List<UserResponse>,
    override val total: Int?
): PaginatedResponse<UserResponse>


@Serializable
data class UserPersonResponse(
    val id: String,
    val name: String,
    val contact: ContactResponse?
)

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val person: UserPersonResponse
)