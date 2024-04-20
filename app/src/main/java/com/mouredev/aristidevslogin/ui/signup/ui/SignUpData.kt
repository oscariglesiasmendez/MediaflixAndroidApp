package com.mouredev.aristidevslogin.ui.signup.ui

data class SignUpData(
    var firstName: String = "",
    var lastName : String = "",
    var email: String = "",
    var password: String = "",
    var signUpChecked: Boolean = false
)