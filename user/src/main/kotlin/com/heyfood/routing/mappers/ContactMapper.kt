package com.heyfood.routing.mappers

import com.heyfood.models.Contact
import com.heyfood.routing.requests.ContactRequest
import com.heyfood.routing.responses.ContactResponse

fun ContactRequest.toContact(): Contact {
    return Contact(
        email = this.email,
        cellphone = this.cellphone,
        phone = this.phone
    )
}

fun Contact.toContactResponse() = ContactResponse(
    id = this.id ?: "",
    email = this.email,
    cellphone = this.cellphone,
    phone = this.phone
)