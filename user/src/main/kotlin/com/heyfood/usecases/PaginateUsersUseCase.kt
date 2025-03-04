package com.heyfood.usecases

import com.heyfood.models.User
import com.heyfood.repositories.UserRepository
import com.heyfood.routing.requests.PaginationRequest

class PaginateUsersUseCase(private val repository: UserRepository = UserRepository) {
    suspend fun execute(input: PaginationRequest): Pair<List<User>, Int?> {
        val data = repository.findBy(skip = input.skip, take = input.take)
        val total = if (input.skip == 0) repository.countBy() else null
        return Pair(data, total)
    }
}
