package com.mouredev.aristidevslogin.validations

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
