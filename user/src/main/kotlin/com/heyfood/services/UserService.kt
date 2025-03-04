package com.heyfood.services

import com.heyfood.database.DatabaseConnection
import com.heyfood.exceptions.ConflictException
import com.heyfood.models.PersonType
import com.heyfood.models.User
import com.heyfood.repositories.ContactRepository
import com.heyfood.repositories.PersonRepository
import com.heyfood.repositories.UserRepository
import com.heyfood.routing.mappers.toUser
import com.heyfood.routing.requests.CreateUserRequest
import com.heyfood.routing.requests.PaginationRequest
import com.heyfood.routing.requests.UpdateUserRequest

class UserService(
    private val connection: DatabaseConnection = DatabaseConnection,
    private val repository: UserRepository = UserRepository,
    private val personRepository: PersonRepository = PersonRepository,
    private val contactRepository: ContactRepository = ContactRepository,
) {

    suspend fun create(input: CreateUserRequest): String {
        val model = input.toUser()

        if (model.person == null) {
            throw ConflictException("Person is required")
        }

        val exists = repository.existsBy(
            username = model.username,
        )

        if (exists) {
            throw ConflictException("User already exists")
        }

        connection.getConnection().use { conn ->
            conn.autoCommit = false

            try {
                personRepository.create(model.person!!, conn)

                repository.create(model, conn)

                if (model.person!!.contact != null) {
                    contactRepository.create(model.person!!.contact!!, conn)
                }

                conn.commit()

                return model.id!!
            } catch (e: Exception) {
                conn.rollback()
                throw e
            }
        }
    }

    suspend fun update(id: String, input: UpdateUserRequest) {
        val model = input.toUser()

        // TODO: Implement update logic
    }

    suspend fun find(id: String): User? {
        return repository.find(id)
    }

    suspend fun paginate(input: PaginationRequest): Pair<List<User>, Int?> {
        val data: List<User> = repository.findBy(
            skip = input.skip,
            take = input.take,
        )
        var total: Int? = null

        if (input.skip == 0) {
            total = repository.countBy()
        }

        return Pair(data, total)
    }
}