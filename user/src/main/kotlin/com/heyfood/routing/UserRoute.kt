package com.heyfood.routing

import com.heyfood.plugins.toPaginationRequest
import com.heyfood.routing.mappers.toFindUserResponse
import com.heyfood.routing.mappers.toPaginatedUserResponse
import com.heyfood.routing.requests.CreateUserRequest
import com.heyfood.routing.requests.UpdateUserRequest
import com.heyfood.routing.responses.CreatedResponse
import com.heyfood.services.UserService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(
    userService: UserService
) {
    post {
        val input = call.receive<CreateUserRequest>()
        val id = userService.create(input)
        call.respond(HttpStatusCode.Created, CreatedResponse(id))
    }

    put("/{id}") {
        val id: String = call.parameters["id"]
            ?: return@put call.respond(HttpStatusCode.BadRequest)
        val input = call.receive<UpdateUserRequest>()
        userService.update(id, input)
        call.respond(HttpStatusCode.NoContent)
    }

    get {
        val input = call.toPaginationRequest()
        val (data, total) = userService.paginate(input)

        call.respond(toPaginatedUserResponse(data, total))
    }

    get("/{id}") {
        val id: String = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)
        val user = userService.find(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(user.toFindUserResponse())
    }
}