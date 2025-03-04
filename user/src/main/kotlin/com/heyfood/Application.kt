package com.heyfood

import com.heyfood.plugins.configureContentNegotiation
import com.heyfood.plugins.configureException
import com.heyfood.plugins.configureValidation
import com.heyfood.routing.configureRouting
import com.heyfood.services.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userService = UserService()

    configureContentNegotiation()
    configureException()
    configureValidation()
    configureRouting(userService)
}
