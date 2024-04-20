package com.mouredev.aristidevslogin.ui.login.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposeauthui.components.CButton
import com.example.jetpackcomposeauthui.components.CTextField
import com.example.jetpackcomposeauthui.components.DontHaveAccountRow
import com.mouredev.aristidevslogin.ui.theme.AlegreyaFontFamily
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily
import kotlinx.coroutines.launch
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ui.login.ui.LoginData
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel

const val TAG_LOG: String = "LoginScreen"

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavHostController) {
    val gameUiState by viewModel.uiState.collectAsState()

    Login(
        viewModel,
        viewModel.loginData,
        gameUiState.loginEnable,
        gameUiState.isLoading,
        gameUiState.loginMessage,
        navController
    )

}

// Function to generate a Toast
private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

/*
@Composable
fun Login(
    modifier: Modifier,
    viewModel: LoginViewModel,
    loginData: LoginData,
    loginEnable: Boolean,
    isLoading: Boolean,
    loginMessage: String
) {
    val coroutineScope = rememberCoroutineScope()
    val mContext = LocalContext.current
    //TODO: Cambiar por el verticalAlign
    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

    } else {
        Column(modifier = modifier) {
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(loginData) { viewModel.onLoginChanged(it, loginData.password) }
            Spacer(modifier = Modifier.padding(16.dp))
            PasswordField(loginData) { viewModel.onLoginChanged(loginData.email, it) }
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    Log.d(TAG_LOG, "lanzando corrutina de login")
                    viewModel.onLoginSelected()
                }
            }
            if (loginData.loginChecked) {
                mToast(mContext, loginMessage)
                viewModel.onToastShowed()
            }
        }

    }
}
*/

@Composable
fun Login(
    viewModel: LoginViewModel,
    loginData: LoginData,
    loginEnable: Boolean,
    isLoading: Boolean,
    loginMessage: String,
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
        // we can copy and paste and do changes for signup screen
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
                            .offset(x = (-20).dp),
                        )

                    Text(
                        text = "Iniciar Sesión",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontFamily = AlegreyaFontFamily,
                            fontWeight = FontWeight(500),
                            color = Color.White
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Text(
                        "Inicia sesión para acceder a todo el contenido disponible",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = AlegreyaSansFontFamily,
                            color = Color(0xB2FFFFFF)
                        ),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )

                    // Email
                    CTextField(
                        { viewModel.onLoginChanged(it, loginData.password) },
                        hint = "Dirección de correo electrónico",
                        value = loginData.email
                    )

                    // Password
                    CTextField(
                        { viewModel.onLoginChanged(loginData.email, it) },
                        hint = "Contraseña",
                        value = loginData.password
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón login
                    CButton({ coroutineScope.launch {
                            Log.d(TAG_LOG, "lanzando corrutina de login")
                            viewModel.onLoginSelected()
                        }
                    }, text = "Iniciar Sesión")

                    DontHaveAccountRow(
                        onSignupTap = {
                            navController.navigate("signup")
                        }
                    )
                }
            }
        }
        if (loginData.loginChecked) {
            mToast(mContext, loginMessage)
            viewModel.onToastShowed()
        }
    }
}


@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = {
            Log.d(TAG_LOG, "Onclick")
            onLoginSelected()
        }, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp), colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.White,
        ), enabled = loginEnable
    ) {
        Text(text = "Iniciar sesion")
    }

}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidaste la contraseña?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(loginData: LoginData, onLoginChanged: (String) -> Unit) {

    //https://stackoverflow.com/questions/67320990/android-jetpack-compose-cant-set-backgroundcolor-for-outlinedtextfield
    TextField(
        value = loginData.email, onValueChange = onLoginChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(loginData: LoginData, onLoginChanged: (String) -> Unit) {
    //https://stackoverflow.com/questions/67320990/android-jetpack-compose-cant-set-backgroundcolor-for-outlinedtextfield
    TextField(
        value = loginData.password, onValueChange = onLoginChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )


}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Header",
        modifier = modifier
    )
}



