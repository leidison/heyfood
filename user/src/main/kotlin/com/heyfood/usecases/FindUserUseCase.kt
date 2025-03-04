package com.heyfood.usecases

import com.heyfood.models.User
import com.heyfood.repositories.UserRepository

class FindUserUseCase(private val repository: UserRepository = UserRepository) {
    suspend fun execute(id: String): User? {
        return repository.find(id)
    }
}
