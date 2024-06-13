package com.mouredev.aristidevslogin.ui.login.ui

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
