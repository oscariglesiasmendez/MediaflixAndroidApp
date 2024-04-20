package com.mouredev.aristidevslogin.ui.signup.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.components.CButton
import com.example.jetpackcomposeauthui.components.CTextField
import com.example.jetpackcomposeauthui.components.CTextFieldWithError
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ui.login.ui.LoginData
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpData
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpViewModel
import com.mouredev.aristidevslogin.ui.theme.AlegreyaFontFamily
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily


@Composable
fun SignupScreen(viewModel: SignUpViewModel, navController: NavHostController) {
    val gameUiState by viewModel.uiState.collectAsState()

    Signup(
        viewModel,
        viewModel.signUpDate,
        gameUiState.signUpEnable,
        gameUiState.isLoading,
        gameUiState.sigUpMessage,
        navController
    )

}


// Function to generate a Toast
private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


@Composable
fun Signup(
    viewModel: SignUpViewModel,
    signUpData: SignUpData,
    signUpEnable: Boolean,
    isLoading: Boolean,
    signUpMessage: String,
    navController: NavHostController
) {

    val coroutineScope = rememberCoroutineScope()
    val mContext = LocalContext.current
    //TODO: Cambiar por el verticalAlign
    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

    } else {
        Surface(
            color = Color(204, 173, 228),
            modifier = Modifier.fillMaxSize()
        ) {

            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {

                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 54.dp)
                            .height(100.dp)
                            .align(Alignment.Start)
                            .offset(x = (-20).dp)
                    )

                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontFamily = AlegreyaFontFamily,
                            fontWeight = FontWeight(500),
                            color = Color.White
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Text(
                        "Registrate ahora para acceder a un portal de diversión sin límites",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = AlegreyaSansFontFamily,
                            color = Color(0xB2FFFFFF)
                        ),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )

                    // FirstName
/*
                    CTextField(
                        {
                            viewModel.onSignUpChanged(
                                it,
                                signUpData.lastName,
                                signUpData.email,
                                signUpData.password
                            )
                        },
                        hint = "Nombre",
                        value = signUpData.firstName
                    )
*/


                    CTextFieldWithError(
                        hint = "Nombre",
                        value = signUpData.firstName,
                        onValueChange = { viewModel.onSignUpChanged(it, signUpData.lastName, signUpData.email, signUpData.password) },
                        errorMessage = "Nombre muy corto. Debe tener al menos 2 caracteres",
                        isEnabled = true,
                        isValid = viewModel.isValidFirstName(signUpData.firstName) // Pass the function
                    )


                    // LastName
                    CTextField(
                        {
                            viewModel.onSignUpChanged(
                                signUpData.firstName,
                                it,
                                signUpData.email,
                                signUpData.password
                            )
                        },
                        hint = "Apellidos",
                        value = signUpData.lastName
                    )

                    // Email
                    CTextField(
                        {
                            viewModel.onSignUpChanged(
                                signUpData.firstName,
                                signUpData.lastName,
                                it,
                                signUpData.password
                            )
                        },
                        hint = "Dirección de correo electrónico",
                        value = signUpData.email
                    )

                    // Password
                    CTextField(
                        {
                            viewModel.onSignUpChanged(
                                signUpData.firstName,
                                signUpData.lastName,
                                signUpData.email,
                                it
                            )
                        },
                        hint = "Contraseña",
                        value = signUpData.password
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CButton(text = "Registrarse")

                    Row(
                        modifier = Modifier.padding(top = 12.dp, bottom = 52.dp)
                    ) {
                        Text(
                            "Ya tienes una cuenta? ",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = AlegreyaSansFontFamily,
                                color = Color.White
                            )
                        )

                        Text("Sign In",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = AlegreyaSansFontFamily,
                                fontWeight = FontWeight(800),
                                color = Color.White
                            ),
                            modifier = Modifier.clickable {
                                navController.navigate("login")
                            }
                        )


                    }
                }
            }

        }

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun SignupScreenPreview() {
    val navController = rememberNavController()
    SignupScreen(viewModel(), navController = navController)
}