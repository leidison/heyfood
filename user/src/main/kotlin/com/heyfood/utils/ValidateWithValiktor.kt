package com.heyfood.utils

import com.heyfood.plugins.Validatable
import io.ktor.server.plugins.requestvalidation.*
import org.valiktor.ConstraintViolationException


inline fun <reified T : Validatable> RequestValidationConfig.validateWithValiktor() {
    validate<T> { request ->
        try {
            request.validate()
            ValidationResult.Valid
        } catch (e: ConstraintViolationException) {
            ValidationResult.Invalid(e.constraintViolations.map {
                "${it.property}: ${it.constraint.name}"
            })
        }
    }
}