package com.mouredev.aristidevslogin

sealed class ScreenRoutes {
    data class WelcomeScreen(val name: String = "Welcome") : ScreenRoutes()
    data class LoginScreen(val name: String = "Login") : ScreenRoutes()
    data class SignUpScreen(val name: String = "SignUp") : ScreenRoutes()
    data class PrincipalScreen(val name: String = "Principal") : ScreenRoutes()
    data class ProductDetailScreen(val name: String = "ProductDetail") : ScreenRoutes()
}