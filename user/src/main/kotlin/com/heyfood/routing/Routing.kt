package com.heyfood.routing

import com.heyfood.usecases.UserUseCases
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCases: UserUseCases = UserUseCases()
) {
    routing {
        route("/api/user") {
            userRoute(userUseCases)
        }
    }
}