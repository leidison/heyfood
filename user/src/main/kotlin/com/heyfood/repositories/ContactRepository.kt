package com.heyfood.repositories

import com.heyfood.database.DatabaseConnection
import com.heyfood.models.Contact
import java.sql.Connection
import java.sql.Timestamp
import java.util.*

object ContactRepository {
    suspend fun create(contact: Contact, conn: Connection? = null) {
        if (contact.person == null) {
            throw IllegalArgumentException("Contact must have a person")
        }

        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val now = Timestamp(System.currentTimeMillis())

        connection.prepareStatement(
            """
            INSERT INTO contact (id, email, cellphone, phone, person_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()
        ).use { stmt ->
            contact.id = contact.id ?: UUID.randomUUID().toString()

            stmt.setString(1, contact.id)
            stmt.setString(2, contact.email)
            stmt.setString(3, contact.cellphone)
            stmt.setString(4, contact.phone)
            stmt.setString(5, contact.person!!.id)
            stmt.setTimestamp(6, now)
            stmt.setTimestamp(7, now)

            stmt.executeUpdate()
        }
    }

    suspend fun update(contact: Contact, conn: Connection? = null) {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val now = Timestamp(System.currentTimeMillis())

        connection.prepareStatement(
            """
            UPDATE contact
            SET email = ?,
                cellphone = ?,
                phone = ?,
                updated_at = ?
            WHERE id = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, contact.email)
            stmt.setString(2, contact.cellphone)
            stmt.setString(3, contact.phone)
            stmt.setTimestamp(4, now)
            stmt.setString(5, contact.id)

            stmt.executeUpdate()
        }
    }

    suspend fun find(id: String, conn: Connection? = null): Contact? {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
            SELECT
                contact.id AS contact_id,
                contact.email AS contact_email,
                contact.cellphone as contact_cellphone,
                contact.phone AS contact_phone
            FROM contact
            WHERE contact.id = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, id)

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                Contact(
                    id = rs.getString("contact_id"),
                    email = rs.getString("contact_email"),
                    cellphone = rs.getString("contact_cellphone"),
                    phone = rs.getString("contact_phone")
                )
            } else {
                null
            }
        }
    }

    suspend fun findBy(take: Int = 50, skip: Int = 0, conn: Connection? = null): List<Contact> {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
                SELECT
                    contact.id AS contact_id,
                    contact.email AS contact_email,
                    contact.cellphone as contact_cellphone,
                    contact.phone AS contact_phone
                FROM contact 
                    LEFT JOIN contact 
                        ON contact.id = contact.contact_id
                LIMIT ?
                OFFSET ?
                """
        ).use { stmt ->
            stmt.setInt(1, take)
            stmt.setInt(2, skip)

            val rs = stmt.executeQuery()

            val users = mutableListOf<Contact>()

            while (rs.next()) {
                users.add(
                    Contact(
                        id = rs.getString("contact_id"),
                        email = rs.getString("contact_email"),
                        cellphone = rs.getString("contact_cellphone"),
                        phone = rs.getString("contact_phone")
                    )
                )
            }

            return users
        }
    }

    suspend fun countBy(conn: Connection? = null): Int {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
            SELECT COUNT(*) AS total
            FROM contact
            """.trimIndent()
        ).use { stmt ->
            val rs = stmt.executeQuery()

            return if (rs.next()) {
                rs.getInt("total")
            } else {
                0
            }
        }
    }
}
