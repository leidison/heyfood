package com.heyfood.routing.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreatedResponse<ID: Any> (val id: ID)