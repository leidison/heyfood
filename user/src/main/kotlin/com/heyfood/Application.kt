package com.heyfood

import com.heyfood.routing.configureRouting
import com.heyfood.services.UserService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userService = UserService()

    install(ContentNegotiation) {
        json()
    }

    configureRouting(userService)
}
