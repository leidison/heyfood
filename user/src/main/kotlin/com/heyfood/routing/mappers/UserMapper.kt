package com.heyfood.routing.mappers

import com.heyfood.models.Contact
import com.heyfood.models.Person
import com.heyfood.models.PersonType
import com.heyfood.models.User
import com.heyfood.routing.requests.ContactRequest
import com.heyfood.routing.requests.CreateUserRequest
import com.heyfood.routing.requests.UpdateUserRequest
import com.heyfood.routing.responses.*

private fun toUser(name: String, inputContact: ContactRequest?): User {
    val person = Person(
        type = PersonType.INDIVIDUAL,
        document = null,
        name = name
    )

    val contact: Contact? = inputContact?.toContact()

    if (contact != null) {
        contact.person = person

        person.contact = contact
    }

    return User(
        username = contact?.email ?: "",
        person = person
    )
}

fun CreateUserRequest.toUser() = toUser(this.name ?: "", this.contact)

fun UpdateUserRequest.toUser() = toUser(this.name ?: "", this.contact)

fun User.toFindUserResponse() = FindUserResponse(
    id = this.id ?: "",
    username = this.username ?: "",
    person = FindUserPersonResponse(
        id = this.id ?: "",
        name = this.person?.name ?: "",
        contact = this.person?.contact?.toContactResponse()
    )
)

fun User.toUserResponse() = UserResponse(
    id = this.id ?: "",
    username = this.username ?: "",
    person = UserPersonResponse(
        id = this.id ?: "",
        name = this.person?.name ?: "",
        contact = this.person?.contact?.toContactResponse()
    )
)

fun toPaginatedUserResponse(data: List<User>, total: Int?) = PaginatedUserResponse(
    data = data.map { it.toUserResponse() },
    total = total
)