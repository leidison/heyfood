package com.heyfood.services

import com.heyfood.models.Contact
import com.heyfood.models.Person
import com.heyfood.models.User
import com.heyfood.routing.mappers.toUser
import com.heyfood.routing.requests.CreateUserRequest
import com.heyfood.routing.requests.PaginationRequest
import com.heyfood.routing.requests.UpdateUserRequest
import com.heyfood.routing.responses.PaginatedResponse

class UserService {
    suspend fun create(input: CreateUserRequest): String {
        val model = input.toUser()

        return model.id
    }

    suspend fun update(id: String, input: UpdateUserRequest) {
        val model = input.toUser()

        // TODO: Implement update logic
    }

    suspend fun find(id: String): User? {
        // TODO: Implement find logic
        return User(
            id = id,
            username = "username",
            person = Person(
                id= "personid",
                name = "name",
                contact = Contact(
                    id = "contactid",
                    email = "email",
                    cellphone = "cellphone",
                    phone = "phone"
                )
            )
        )
    }

    suspend fun paginate(input: PaginationRequest): Pair<List<User>, Int?> {
        var total: Int? = null
        var data: List<User> = listOf()

        // TODO: Implement pagination logic

        if (input.skip == 0) {
            // TODO: Implement total logic
            total = 0
        }

        return Pair(data, total)
    }
}