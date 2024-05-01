package com.mouredev.aristidevslogin

sealed class Screens1 (val screens: String){
    data object PrincipalScreen : Screens1("Principal")
    data object ProductScreen : Screens1("Products")
    data object BookScreen : Screens1("Books")
    data object MovieScreen : Screens1("Movies")
    data object GameScreen : Screens1("Games")
}