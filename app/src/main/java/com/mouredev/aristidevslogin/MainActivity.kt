package com.mouredev.aristidevslogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mouredev.aristidevslogin.ui.theme.AristiDevsLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AristiDevsLoginTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}

/*
@Composable
fun NavigationView() {

    val navController = rememberNavController()
    val loginViewModel =  LoginViewModel()
    val signUpViewModel = SignUpViewModel()

    NavHost(navController = navController, startDestination = "welcome" ){
        // also pass navController to each screen so we can use navController in there
        composable("welcome"){ WelcomeScreen(navController) }
        composable("login"){ LoginScreen(loginViewModel, navController) }
        composable("signup"){ SignupScreen(signUpViewModel, navController) }
    }

}*/