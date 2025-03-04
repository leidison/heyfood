package com.heyfood.routing.requests
import com.heyfood.plugins.Validatable
import kotlinx.serialization.Serializable
import org.valiktor.functions.*
import org.valiktor.validate

@Serializable
data class CreateUserRequest (val name: String?, val contact: ContactRequest?): Validatable  {
    override fun validate() {
        validate(this) {
            validate(CreateUserRequest::name).isNotNull().hasSize(min = 3, max = 255)
        }
        contact?.validate()
    }
}