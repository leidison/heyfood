package com.heyfood.exceptions

data class ConflictException(
    override val message: String
): RuntimeException(message)