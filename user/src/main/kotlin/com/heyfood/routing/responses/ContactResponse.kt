package com.heyfood.routing.responses

import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse(val id: String, val email: String?, val cellphone: String?, val phone: String?)
