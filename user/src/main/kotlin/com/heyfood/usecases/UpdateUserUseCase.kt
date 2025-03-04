package com.heyfood.usecases

import com.heyfood.routing.requests.UpdateUserRequest
import com.heyfood.repositories.UserRepository
import com.heyfood.routing.mappers.toUser

class UpdateUserUseCase(private val repository: UserRepository = UserRepository) {
    suspend fun execute(id: String, input: UpdateUserRequest) {
        val model = input.toUser()

        // TODO: Implementar lógica de atualização no banco de dados
    }
}
