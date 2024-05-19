package com.mouredev.aristidevslogin.ui.login.screen


import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.mouredev.aristidevslogin.ui.theme.AlegreyaFontFamily
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily
import com.mouredev.aristidevslogin.components.CButton
import com.mouredev.aristidevslogin.components.CPasswordTextField
import com.mouredev.aristidevslogin.components.CTextField
import com.mouredev.aristidevslogin.components.DontHaveAccountRow
import com.mouredev.aristidevslogin.ui.login.ui.LoginViewModel
import com.mouredev.aristidevslogin.ui.login.ui.LoginFormEvent
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.ui.login.ui.LoginScreenViewmodel
import com.stripe.exception.ApiException

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {

    // Token para auth con google
    val token = "345794833085-h4bpo55kna5rs44epnvdb5o7kg5gouh6.apps.googleusercontent.com"
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {

        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredential(credential) {
                onLoginClick()
            }
        } catch (ex: Exception) {
            Log.d("Mediaflix", "Falló el registro con google")
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }



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
                    hint = "Dirección de correo electrónico",
                    onValueChange = {
                        viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                    },
                    value = viewModel.state.email,
                    errorMessage = viewModel.state.emailError
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                CPasswordTextField(
                    hint = "Contraseña",
                    value = viewModel.state.password,
                    onValueChange = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
                    errorMessage = viewModel.state.passwordError,
                    passwordVisible = passwordVisible,
                    passwordVisibleChange = { passwordVisible = !passwordVisible },
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón login
                CButton(
                    onClick = {

                        viewModel.signInWithEmailAndPassword(
                            viewModel.state.email,
                            viewModel.state.password
                        ) {
                            onLoginClick()
                        }

                        /*
                        // Trigger LoginFormEvent.Submit first
                        viewModel.onEvent(LoginFormEvent.Submit)

                        // Sólo si los datos son válidos avanzo a la siguiente pantalla
                        if(viewModel.submitData()) {
                            // Then trigger onLoginClick
                            onLoginClick()
                        }
                        */
                    },
                    text = "Iniciar Sesión"
                )

                DontHaveAccountRow(onSignUpClick)

                Text("o entra con ",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = AlegreyaSansFontFamily,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    horizontalArrangement = Arrangement.Center
                ) {

                    IconButton(
                        onClick = {

                            val opciones = GoogleSignInOptions.Builder(
                                GoogleSignInOptions.DEFAULT_SIGN_IN
                            ).requestIdToken(token).requestEmail().build()
                            val googleSingInClient = GoogleSignIn.getClient(context, opciones)
                            launcher.launch(googleSingInClient.signInIntent)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google_icon),
                            contentDescription = "Google Icon",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified
                        )
                    }

                    IconButton(
                        onClick = {

                            val opciones = GoogleSignInOptions.Builder(
                                GoogleSignInOptions.DEFAULT_SIGN_IN
                            ).requestIdToken(token).requestEmail().build()
                            val googleSingInClient = GoogleSignIn.getClient(context, opciones)
                            launcher.launch(googleSingInClient.signInIntent)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google_icon),
                            contentDescription = "Twitter Icon",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified
                        )
                    }


                }

                Spacer(modifier = Modifier.height(16.dp))


            }
        }
    }
}





