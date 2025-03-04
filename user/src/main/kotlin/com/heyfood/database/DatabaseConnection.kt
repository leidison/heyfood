package com.heyfood.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

object DatabaseConnection {
    private val dataSource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/heyfood"
            driverClassName = "org.postgresql.Driver"
            schema = "user"
            username = "heyfood"
            password = "heyfood"
            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 60000
        }

        dataSource = HikariDataSource(config)
    }

    fun getConnection() = dataSource.connection
}