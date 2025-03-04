package com.heyfood.repositories

import com.heyfood.database.DatabaseConnection
import com.heyfood.models.Contact
import com.heyfood.models.Person
import com.heyfood.models.User

object UserRepository {
    suspend fun create(user: User) {
        DatabaseConnection.getConnection().use { conn ->
            conn.prepareStatement("""
                INSERT INTO "user" (id, username, person_id)
                VALUES (?, ?, ?)
            """.trimIndent()).use { stmt ->
                stmt.setString(1, user.id)
                stmt.setString(2, user.username)
                stmt.setString(3, user.person.id)

                stmt.executeUpdate()
            }
        }
    }

    suspend fun update(user: User) {
        DatabaseConnection.getConnection().use { conn ->
            conn.prepareStatement("""
                UPDATE "user"
                SET username = ?
                WHERE id = ?
            """.trimIndent()).use { stmt ->
                stmt.setString(1, user.username)
                stmt.setString(2, user.id)

                stmt.executeUpdate()
            }
        }
    }

    suspend fun find(id: String): User? {
        DatabaseConnection.getConnection().use {conn ->
            conn.prepareStatement("""
                SELECT
                    "user".id AS user_id,
                    "user".username AS user_username,
                    person.id AS person_id,
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
            """.trimIndent()).use { stmt ->
                stmt.setString(1, id)

                val rs = stmt.executeQuery()

                return if (rs.next()) {
                    User(
                        id = rs.getString("user_id"),
                        username = rs.getString("user_username"),
                        person = Person(
                            id = rs.getString("person_id"),
                            name = rs.getString("person_name"),
                            contact = Contact(
                                id= rs.getString("contact_id"),
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
    }

    suspend fun findBy(take: Int = 50, skip: Int = 0): List<User> {
        DatabaseConnection.getConnection().use { conn ->
            conn.prepareStatement(
                """
                SELECT
                    "user".id AS user_id,
                    "user".username AS user_username,
                    person.id AS person_id,
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
                                name = rs.getString("person_name"),
                                contact = Contact(
                                    id= rs.getString("contact_id"),
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
    }

    suspend fun countBy(): Int {
        DatabaseConnection.getConnection().use { conn ->
            conn.prepareStatement("""
                SELECT COUNT(*) AS total
                FROM "user"
            """.trimIndent()).use { stmt ->
                val rs = stmt.executeQuery()

                return if (rs.next()) {
                    rs.getInt("total")
                } else {
                    0
                }
            }
        }
    }
}
