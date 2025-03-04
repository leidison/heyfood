package com.heyfood.models

data class Contact(
    var id: String? = null,
    val email: String?,
    val cellphone: String?,
    val phone: String?,
    var person: Person? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)