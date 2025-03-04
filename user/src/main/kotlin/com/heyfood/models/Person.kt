package com.heyfood.models

enum class PersonType {
    INDIVIDUAL,
    COMPANY
}

data class Person(
    var id: String? = null,
    val type: PersonType?,
    val document: String?,
    val name: String?,
    var contact: Contact? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)