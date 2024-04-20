package com.mouredev.aristidevslogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mouredev.aristidevslogin.ui.login.screen.LoginScreen
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel
import com.mouredev.aristidevslogin.ui.signup.screen.SignupScreen
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpViewModel
import com.mouredev.aristidevslogin.ui.welcome.WelcomeScreen
import com.mouredev.aristidevslogin.ui.theme.AristiDevsLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AristiDevsLoginTheme {
                NavigationView()
            }
        }
    }
}

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

}