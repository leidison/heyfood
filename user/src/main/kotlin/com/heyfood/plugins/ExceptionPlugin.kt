package com.heyfood.plugins

import com.heyfood.exceptions.ConflictException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureException() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.UnprocessableEntity, cause.reasons.joinToString())
        }
        exception<ConflictException> { call, cause ->
            call.respond(HttpStatusCode.Conflict, cause.message)
        }
    }
}
