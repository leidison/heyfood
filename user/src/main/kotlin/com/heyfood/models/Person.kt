package com.heyfood.models

import java.util.*

data class Person(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val contact: Contact?
)