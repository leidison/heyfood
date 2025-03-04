package com.heyfood.services

import com.heyfood.models.User
import com.heyfood.repositories.UserRepository
import com.heyfood.routing.mappers.toUser
import com.heyfood.routing.requests.CreateUserRequest
import com.heyfood.routing.requests.PaginationRequest
import com.heyfood.routing.requests.UpdateUserRequest

class UserService(
    private val repository: UserRepository = UserRepository
) {

    suspend fun create(input: CreateUserRequest): String {
        val model = input.toUser()

        return model.id
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