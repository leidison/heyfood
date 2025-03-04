package com.heyfood.models

data class User(
    var id: String? = null,
    val username: String?,
    var person: Person? = null
)