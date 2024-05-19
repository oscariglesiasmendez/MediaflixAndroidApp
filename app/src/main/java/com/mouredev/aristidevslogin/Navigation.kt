package com.mouredev.aristidevslogin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.mouredev.aristidevslogin.ui.login.screen.LoginScreen
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel
import com.mouredev.aristidevslogin.ui.principal.screens.PrincipalScreen
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

        navigation(startDestination = ScreenRoutes.WelcomeScreen.route, route = "welcome_flow") {
            composable(route = ScreenRoutes.WelcomeScreen.route) {
                if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
                    WelcomeScreen(navController = navHostController)
                }else{
                    PrincipalScreen()
                }
            }

            composable(route = ScreenRoutes.LoginScreen.route) {
                LoginScreen(
                    loginViewModel,
                    onLoginClick = {
                        navHostController.navigate(
                            ScreenRoutes.PrincipalScreen.route
                        )
                    },
                    onSignUpClick = {
                        navHostController.navigateToSingleTop(
                            ScreenRoutes.SignUpScreen.route
                        )
                    }
                )
            }

            composable(route = ScreenRoutes.SignUpScreen.route) {
                SignUpScreen(
                    signUpViewModel,
                    onSignUpClick = {
                        navHostController.navigate(
                            ScreenRoutes.PrincipalScreen.route
                        )
                    },
                    onLoginClick = {
                        navHostController.navigateToSingleTop(
                            ScreenRoutes.LoginScreen.route
                        )
                    }
                )
            }

            composable(route = ScreenRoutes.PrincipalScreen.route) {
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

