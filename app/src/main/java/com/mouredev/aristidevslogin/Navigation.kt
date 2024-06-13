package com.mouredev.aristidevslogin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
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


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "welcome_flow",
    ) {

        val loginViewModel = LoginViewModel()
        val signUpViewModel = SignUpViewModel()

        //val clientRepository = ClientsRepositoryImpl(RetrofitInstance.api)
        //val clientViewModel = ClientViewModel(clientRepository)

        navigation(startDestination = ScreenRoutes.WelcomeScreen.route, route = "welcome_flow") {
            composable(route = ScreenRoutes.WelcomeScreen.route) {
                WelcomeScreen(navController = navHostController)
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
                    //clientViewModel,
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

