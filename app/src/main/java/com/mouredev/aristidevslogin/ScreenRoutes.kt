package com.mouredev.aristidevslogin


sealed class ScreenRoutes(val route:String) {
    object WelcomeScreen : ScreenRoutes("Welcome")
    object LoginScreen: ScreenRoutes("Login")
    object SignUpScreen: ScreenRoutes("SignUp")
    object PrincipalScreen: ScreenRoutes("Principal")
    object ProductDetailScreen: ScreenRoutes("ProductDetail")
    object UserManualScreen: ScreenRoutes("UserManual")
    object OrdersScreen: ScreenRoutes("Orders")
    object CartScreen: ScreenRoutes("Cart")
    object ContactScreen: ScreenRoutes( "Contact")
    object ProductScreen: ScreenRoutes("Product")
    object BookScreen: ScreenRoutes("Book")
    object MovieScreen: ScreenRoutes("Movie")
    object GameScreen: ScreenRoutes( "Game")
}
