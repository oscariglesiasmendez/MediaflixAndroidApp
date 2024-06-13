package com.mouredev.aristidevslogin.validations

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if(password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "La contraseña tiene que contener al menos 6 caracteres"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "La contraseña necesita tener al menos un dígito y un caracter"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}