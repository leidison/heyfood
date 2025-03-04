package com.heyfood.plugins

import com.heyfood.routing.requests.CreateUserRequest
import com.heyfood.routing.requests.UpdateUserRequest
import com.heyfood.utils.validateWithValiktor
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

interface Validatable {
    fun validate()
}

fun Application.configureValidation() {
    install(RequestValidation) {
        validateWithValiktor<CreateUserRequest>()
        validateWithValiktor<UpdateUserRequest>()
    }
}
