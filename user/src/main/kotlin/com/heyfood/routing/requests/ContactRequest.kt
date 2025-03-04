package com.heyfood.routing.requests
import kotlinx.serialization.Serializable
import org.valiktor.functions.*
import org.valiktor.validate

@Serializable
data class ContactRequest (val email: String?, val cellphone: String?, val phone: String?) {
    fun validate() {
        validate(this) {
            validate(ContactRequest::email).isNotNull().isEmail().hasSize(min = 3, max = 255)
            validate(ContactRequest::cellphone).isNotBlank().hasSize(min = 3, max = 255)
            validate(ContactRequest::phone).isNotBlank().hasSize(min = 3, max = 255)
        }
    }
}