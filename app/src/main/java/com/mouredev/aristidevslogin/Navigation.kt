package com.mouredev.aristidevslogin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mouredev.aristidevslogin.ui.login.screen.LoginScreen
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel
import com.mouredev.aristidevslogin.ui.principal.screen.PrincipalScreen
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpViewModel
import com.mouredev.aristidevslogin.ui.welcome.WelcomeScreen
import com.mouredev.aristidevslogin.ui.signup.screen.SignUpScreen


@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "welcome_flow",
    ) {

        val loginViewModel = LoginViewModel()
        val signUpViewModel = SignUpViewModel()

        navigation(startDestination = ScreenRoutes.WelcomeScreen().name, route = "welcome_flow") {
            composable(route = ScreenRoutes.WelcomeScreen().name) {
                WelcomeScreen(navController = navHostController)
            }

            composable(route = ScreenRoutes.LoginScreen().name) {
                LoginScreen(
                    loginViewModel,
                    onLoginClick = {
                        navHostController.navigate(
                            ScreenRoutes.PrincipalScreen().name
                        )
                    },
                    onSignUpClick = {
                        navHostController.navigateToSingleTop(
                            ScreenRoutes.SignUpScreen().name
                        )
                    }
                )
            }

            composable(route = ScreenRoutes.SignUpScreen().name) {
                SignUpScreen(
                    signUpViewModel,
                    onSignUpClick = {
                        navHostController.navigate(
                            ScreenRoutes.PrincipalScreen().name
                        )
                    },
                    onLoginClick = {
                        navHostController.navigateToSingleTop(
                            ScreenRoutes.LoginScreen().name
                        )
                    }
                )
            }

            composable(route = ScreenRoutes.PrincipalScreen().name) {
                PrincipalScreen()
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

