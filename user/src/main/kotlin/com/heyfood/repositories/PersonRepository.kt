package com.heyfood.repositories

import com.heyfood.database.DatabaseConnection
import com.heyfood.models.Contact
import com.heyfood.models.Person
import com.heyfood.models.PersonType
import java.sql.Connection
import java.sql.Timestamp
import java.util.*

object PersonRepository {
    suspend fun create(person: Person, conn: Connection? = null) {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val now = Timestamp(System.currentTimeMillis())

        connection.prepareStatement(
            """
            INSERT INTO person (id, type, document, name, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent()
        ).use { stmt ->
            person.id = person.id ?: UUID.randomUUID().toString()

            stmt.setString(1, person.id)
            stmt.setString(2, person.type.toString())
            stmt.setString(3, person.document)
            stmt.setString(4, person.name)
            stmt.setTimestamp(5, now)
            stmt.setTimestamp(6, now)

            stmt.executeUpdate()
        }
    }

    suspend fun update(person: Person, conn: Connection? = null) {
        if (person.id == null) {
            throw IllegalArgumentException("Person id is required")
        }

        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val now = Timestamp(System.currentTimeMillis())

        connection.prepareStatement(
            """
            UPDATE person
            SET document = ?,
                name = ?,
                updatedAt = ?
            WHERE id = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, person.document)
            stmt.setString(2, person.name)
            stmt.setTimestamp(3, now)
            stmt.setString(4, person.id)

            stmt.executeUpdate()
        }
    }

    suspend fun find(id: String, conn: Connection? = null): Person? {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
            SELECT
                person.id AS person_id,
                person.type AS person_type,
                person.document AS person_document,
                person.name AS person_name,
                contact.id AS contact_id,
                contact.email AS contact_email,
                contact.cellphone as contact_cellphone,
                contact.phone AS contact_phone
            FROM person
                LEFT JOIN contact 
                    ON person.id = contact.person_id
            WHERE person.id = ?
            """.trimIndent()
        ).use { stmt ->
            stmt.setString(1, id)

            val rs = stmt.executeQuery()

            return if (rs.next()) {
                Person(
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
            } else {
                null
            }
        }
    }

    suspend fun findBy(take: Int = 50, skip: Int = 0, conn: Connection? = null): List<Person> {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
                SELECT
                    person.id AS person_id,
                    person.type AS person_type,
                    person.document AS person_document,
                    person.name AS person_name,
                    contact.id AS contact_id,
                    contact.email AS contact_email,
                    contact.cellphone as contact_cellphone,
                    contact.phone AS contact_phone
                FROM person 
                    LEFT JOIN contact 
                        ON person.id = contact.person_id
                LIMIT ?
                OFFSET ?
                """
        ).use { stmt ->
            stmt.setInt(1, take)
            stmt.setInt(2, skip)

            val rs = stmt.executeQuery()

            val users = mutableListOf<Person>()

            while (rs.next()) {
                users.add(
                    Person(
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
            }

            return users
        }
    }

    suspend fun countBy(conn: Connection? = null): Int {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        connection.prepareStatement(
            """
            SELECT COUNT(*) AS total
            FROM person
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

    suspend fun existsBy(type: String? = null, document: String? = null, conn: Connection? = null): Boolean {
        val connection: Connection = conn ?: DatabaseConnection.getConnection()

        val conditions = mutableListOf<String>()
        val values = mutableListOf<String>()

        if (type != null) {
            conditions.add("person.type = ?")
            values.add(type)
        }

        if (document != null) {
            conditions.add("person.document = ?")
            values.add(document)
        }

        connection.prepareStatement(
            """
            SELECT EXISTS (
                SELECT 1 FROM person 
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
