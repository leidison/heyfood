package com.heyfood

import com.heyfood.database.DatabaseConnection
import com.heyfood.plugins.configureContentNegotiation
import com.heyfood.plugins.configureCors
import com.heyfood.plugins.configureException
import com.heyfood.plugins.configureValidation
import com.heyfood.repositories.UserRepository
import com.heyfood.routing.configureRouting
import com.heyfood.services.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseConnection.getConnection().use {
        println("✅ Connected to database")
    }

    configureCors()
    configureContentNegotiation()
    configureException()
    configureValidation()
    configureRouting()
}
