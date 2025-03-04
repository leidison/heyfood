package com.heyfood.models

import java.util.*

data class Contact(
    val id: String = UUID.randomUUID().toString(),
    val email: String?,
    val cellphone: String?,
    val phone: String?
)