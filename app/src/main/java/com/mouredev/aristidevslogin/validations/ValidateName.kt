package com.mouredev.aristidevslogin.validations

class ValidateName {

    fun execute(name: String): ValidationResult {
        if(name.length < 3) {
            return ValidationResult(
                successful = false,
                errorMessage = "El nombre tiene que contener al menos 3 caracteres"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}