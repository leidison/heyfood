package com.heyfood.routing.requests

import com.heyfood.plugins.Validatable
import kotlinx.serialization.Serializable
import org.valiktor.functions.hasSize
import org.valiktor.functions.isNotNull
import org.valiktor.validate

@Serializable
data class UpdateUserRequest (
    val name: String?,
    val contact: ContactRequest?
): Validatable {
    override fun validate() {
        validate(this) {
            validate(UpdateUserRequest::name).isNotNull().hasSize(min = 3, max = 255)
        }
        contact?.validate()
    }
}