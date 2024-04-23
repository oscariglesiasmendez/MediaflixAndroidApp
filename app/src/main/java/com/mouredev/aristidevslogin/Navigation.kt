package com.mouredev.aristidevslogin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mouredev.aristidevslogin.data.ProductsRepositoryImpl
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.ui.home.screen.HomeScreen
import com.mouredev.aristidevslogin.ui.home.ui.ProductsViewModel
import com.mouredev.aristidevslogin.ui.login.screen.LoginScreen
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpViewModel
import com.mouredev.aristidevslogin.ui.welcome.WelcomeScreen
import com.mouredev.aristidevslogin.ui.signup.screen.SignUpScreen


sealed class Route {
    data class WelcomeScreen(val name: String = "Welcome") : Route()
    data class LoginScreen(val name: String = "Login") : Route()
    data class SignUpScreen(val name: String = "SignUp") : Route()
    data class HomeScreen(val name: String = "Home") : Route()
}


@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "welcome_flow",
    ) {

        val loginViewModel = LoginViewModel()
        val signUpViewModel = SignUpViewModel()
        val homeviewModel = ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.api))

        navigation(startDestination = Route.WelcomeScreen().name, route = "welcome_flow") {
            composable(route = Route.WelcomeScreen().name) {
                WelcomeScreen(onLoginClick = {
                    navHostController.navigate(
                        Route.LoginScreen().name
                    )
                },
                    onSignUpClick = {
                        navHostController.navigateToSingleTop(
                            Route.SignUpScreen().name
                        )
                    })
            }


            composable(route = Route.LoginScreen().name) {
                LoginScreen(
                    loginViewModel,
                    onLoginClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        )
                    },
                    onSignUpClick = {
                        navHostController.navigateToSingleTop(
                            Route.SignUpScreen().name
                        )
                    }
                )
            }


            composable(route = Route.SignUpScreen().name) {
                SignUpScreen(
                    signUpViewModel,
                    onSignUpClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        )
                    },
                    onLoginClick = {
                        navHostController.navigateToSingleTop(
                            Route.LoginScreen().name
                        )
                    }
                )
            }

            composable(route = Route.HomeScreen().name) {
                HomeScreen(homeviewModel)
            }
        }
    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

