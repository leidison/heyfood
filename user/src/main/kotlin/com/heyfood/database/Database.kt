package com.heyfood.database

import io.ktor.server.application.*
import java.sql.*

fun Application.configureDatabase() {
    val url = "jdbc:postgresql://localhost:5432/heyfood"

    val connection: Connection?

    try {
        connection = DriverManager.getConnection(url)

        println("Connected to database")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}