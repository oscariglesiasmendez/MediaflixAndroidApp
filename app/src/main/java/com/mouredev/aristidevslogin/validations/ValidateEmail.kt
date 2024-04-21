package com.mouredev.aristidevslogin.validations

import android.util.Patterns
import androidx.compose.ui.res.stringResource
import com.mouredev.aristidevslogin.R

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "El email no puede estar vacío"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "El email introducido no es un email válido"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}