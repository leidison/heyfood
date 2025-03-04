package com.heyfood.models

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    var person: Person
)