package com.mouredev.aristidevslogin.ui.signup.ui

data class SignUpFormState(
    var firstName: String = "",
    val firstNameError: String? = null,
    var lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
)
