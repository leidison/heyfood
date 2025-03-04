package com.heyfood.routing.responses

import kotlinx.serialization.Serializable

@Serializable
data class FindUserPersonResponse(val id: String, val name: String, val contact: ContactResponse?)

@Serializable
data class FindUserResponse(val id: String, val username: String, val person: FindUserPersonResponse)
