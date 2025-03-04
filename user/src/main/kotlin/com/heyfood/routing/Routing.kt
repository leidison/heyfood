package com.heyfood.routing

import com.heyfood.services.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService) {
    routing {
        route("/api/user") {
            userRoute(userService)
        }
    }
}