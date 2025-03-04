package com.heyfood.usecases

import com.heyfood.database.DatabaseConnection
import com.heyfood.exceptions.ConflictException
import com.heyfood.repositories.ContactRepository
import com.heyfood.repositories.PersonRepository
import com.heyfood.repositories.UserRepository
import com.heyfood.routing.mappers.toUser
import com.heyfood.routing.requests.CreateUserRequest

class CreateUserUseCase(
    private val connection: DatabaseConnection = DatabaseConnection,
    private val userRepository: UserRepository = UserRepository,
    private val personRepository: PersonRepository = PersonRepository,
    private val contactRepository: ContactRepository = ContactRepository
) {
    suspend fun execute(input: CreateUserRequest): String {
        val model = input.toUser()

        if (model.person == null) {
            throw ConflictException("Person is required")
        }

        val exists = userRepository.existsBy(username = model.username)

        if (exists) {
            throw ConflictException("User already exists")
        }

        connection.getConnection().use { conn ->
            conn.autoCommit = false

            try {
                personRepository.create(model.person!!, conn)
                userRepository.create(model, conn)

                model.person!!.contact?.let { contactRepository.create(it, conn) }

                conn.commit()

                return model.id!!
            } catch (e: Exception) {
                conn.rollback()
                throw e
            }
        }
    }
}
