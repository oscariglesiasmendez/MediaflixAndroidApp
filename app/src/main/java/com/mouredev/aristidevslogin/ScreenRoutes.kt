package com.mouredev.aristidevslogin

sealed class ScreenRoutes {
    data class WelcomeScreen(val name: String = "Welcome") : ScreenRoutes()
    data class LoginScreen(val name: String = "Login") : ScreenRoutes()
    data class SignUpScreen(val name: String = "SignUp") : ScreenRoutes()
    data class PrincipalScreen(val name: String = "Principal") : ScreenRoutes()
    data class ProductDetailScreen(val name: String = "ProductDetail") : ScreenRoutes()
    data class ProfileScreen(val name : String = "Profile") : ScreenRoutes()
    data class OrdersScreen(val name : String = "Orders") : ScreenRoutes()
    data class CartScreen(val name : String = "Cart") : ScreenRoutes()
    data class ContactScreen(val name : String = "Contact") : ScreenRoutes()
    data class ProductScreen(val name : String = "Product") : ScreenRoutes()
    data class BookScreen(val name : String = "Book") : ScreenRoutes()
    data class MovieScreen(val name : String = "Movie") : ScreenRoutes()
    data class GameScreen(val name : String = "Movie") : ScreenRoutes()
}