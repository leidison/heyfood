package com.heyfood.repositories

import com.heyfood.database.DatabaseConnection
import com.heyfood.models.Contact
import com.heyfood.models.Person
import com.heyfood.models.PersonType
import com.heyfood.models.User
import java.sql.Connection
import java.sql.Timestamp
import java.util.*

object UserRepository {
    suspend fun create(user: User, conn: Connection? = null) {
        if (user.person?.id == null) {
            throw IllegalArgumentException("Person id is required")
        }

        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val now = Timestamp(System.currentTimeMillis())

        connection.prepareStatement(
            """
            INSERT INTO "user" (id, username, person_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?)
            """.trimIndent()
        ).use { stmt ->
            user.id = user.id ?: UUID.randomUUID().toString()

            stmt.setString(1, user.id)
            stmt.setString(2, user.username)
            stmt.setString(3, user.person!!.id!!)
            stmt.setTimestamp(4, now)
            stmt.setTimestamp(5, now)

            stmt.executeUpdate()
        }
    }

    suspend fun update(user: User, conn: Connection? = null) {
        if (user.id == null) {
            throw IllegalArgumentException("User id is required")
        }

        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val now = Timestamp(System.currentTimeMillis())

        connection.prepareStatement(
            """
            UPDATE "user"
            SET username = ?,
                updated_at = ?
            WHERE id = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, user.username)
            stmt.setTimestamp(2, now)
            stmt.setString(3, user.id)

            stmt.executeUpdate()
        }
    }

    suspend fun find(id: String, conn: Connection? = null): User? {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
            SELECT
                "user".id AS user_id,
                "user".username AS user_username,
                person.id AS person_id,
                person.type AS person_type,
                person.document AS person_document,
                person.name AS person_name,
                contact.id AS contact_id,
                contact.email AS contact_email,
                contact.cellphone as contact_cellphone,
                contact.phone AS contact_phone
            FROM "user"
                INNER JOIN person 
                    ON "user".person_id = person.id
                LEFT JOIN contact 
                    ON person.id = contact.person_id
            WHERE "user".id = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, id)

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                User(
                    id = rs.getString("user_id"),
                    username = rs.getString("user_username"),
                    person = Person(
                        id = rs.getString("person_id"),
                        type = PersonType.valueOf(rs.getString("person_type")),
                        document = rs.getString("person_document"),
                        name = rs.getString("person_name"),
                        contact = Contact(
                            id = rs.getString("contact_id"),
                            email = rs.getString("contact_email"),
                            cellphone = rs.getString("contact_cellphone"),
                            phone = rs.getString("contact_phone")
                        )
                    )
                )
            } else {
                null
            }
        }
    }

    suspend fun findBy(take: Int = 50, skip: Int = 0, conn: Connection? = null): List<User> {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
                SELECT
                    "user".id AS user_id,
                    "user".username AS user_username,
                    person.id AS person_id,
                    person.type AS person_type,
                    person.document AS person_document,
                    person.name AS person_name,
                    contact.id AS contact_id,
                    contact.email AS contact_email,
                    contact.cellphone as contact_cellphone,
                    contact.phone AS contact_phone
                FROM "user"
                    INNER JOIN person 
                        ON "user".person_id = person.id
                    LEFT JOIN contact 
                        ON person.id = contact.person_id
                LIMIT ?
                OFFSET ?
                """
        ).use { stmt ->
            stmt.setInt(1, take)
            stmt.setInt(2, skip)

            val rs = stmt.executeQuery()

            val users = mutableListOf<User>()

            while (rs.next()) {
                users.add(
                    User(
                        id = rs.getString("user_id"),
                        username = rs.getString("user_username"),
                        person = Person(
                            id = rs.getString("person_id"),
                            type = PersonType.valueOf(rs.getString("person_type")),
                            document = rs.getString("person_document"),
                            name = rs.getString("person_name"),
                            contact = Contact(
                                id = rs.getString("contact_id"),
                                email = rs.getString("contact_email"),
                                cellphone = rs.getString("contact_cellphone"),
                                phone = rs.getString("contact_phone")
                            )
                        )
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
            FROM "user"
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

    suspend fun existsBy(username: String? = null, conn: Connection? = null): Boolean {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val conditions = mutableListOf<String>()
        val values = mutableListOf<String>()

        if (username != null) {
            conditions.add(""" "user".username = ?""")
            values.add(username)
        }

        connection.prepareStatement(
            """
            SELECT EXISTS (
                SELECT 1 FROM "user" 
                WHERE ${conditions.joinToString(" AND ")}
            )
            """.trimIndent()
        ).use { stmt ->
            values.forEachIndexed { index, value ->
                stmt.setString(index + 1, value)
            }

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                rs.getBoolean(1)
            } else {
                false
            }
        }

    }
}
