package com.mouredev.aristidevslogin.ui.login.screen


import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
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
import com.mouredev.aristidevslogin.ui.signup.screen.insertClientInApi
import com.stripe.exception.ApiException
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {

    // Token para auth con google
    val token = "345794833085-408nrm6rsh9ai0r2sfofsvgd3k1lkc05.apps.googleusercontent.com"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            // Use the credential to sign in with Firebase
            viewModel.signInWithGoogleCredential(credential) {
                // Insert client into API after successful sign-in
                val userEmail = FirebaseAuth.getInstance().currentUser?.email
                val fullname = FirebaseAuth.getInstance().currentUser?.displayName
                val parts = fullname?.split("\\s+".toRegex())
                val firstname = parts?.get(0)
                val lastname = if (parts?.size!! > 2) {
                    "${parts[1]} ${parts[2]}"
                } else {
                    parts[1]
                }
                if (userEmail != null) {
                    scope.launch {
                        insertClientInApi(userEmail, firstname, lastname)
                        onLoginClick()
                    }
                } else {
                    Toast.makeText(context, "Failed to retrieve user email", Toast.LENGTH_SHORT).show()
                }
                onLoginClick()
            }
        } catch (ex: Exception) {
            Log.d("Mediaflix", "Falló el registro con Google", ex)
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.primary,
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
                            onLoginClick() // navega a la pantalla principal
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
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            val opciones = GoogleSignInOptions
                                .Builder(
                                    GoogleSignInOptions.DEFAULT_SIGN_IN
                                )
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                            val googleSingInClient = GoogleSignIn.getClient(context, opciones)
                            launcher.launch(googleSingInClient.signInIntent)

                            /*
                            val userEmail = FirebaseAuth.getInstance().currentUser?.email

                            // Creo ese cliente en mi API
                            scope.launch {
                                try {
                                    val response =
                                        RetrofitInstance.clientService.searchClientByEmail(
                                            viewModel.state.email
                                        )
                                    if (response.isSuccessful) { // Ya existe un cliente con ese email
                                        Log.d("Mediaflix", "Ya existe un cliente con ese email")
                                        Toast.makeText(
                                            context,
                                            "Ya existe un cliente con ese email. Por favor intenéntelo de nuevo con otro email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        viewModel.createUserWithEmailAndPassword(
                                            viewModel.state.email,
                                            viewModel.state.password,
                                        ) {
                                            onSignUpClick()
                                        }

                                        val now = Instant.now()
                                        val sqlFormattedDate = SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSSX",
                                            Locale.getDefault()
                                        ).format(Date(now.toEpochMilli()))

                                        val client = Client(
                                            clientId = 0,
                                            firstName = "",
                                            lastName = "",
                                            email = userEmail!!,
                                            creationDate = sqlFormattedDate,
                                            available = true
                                        )
                                        scope.launch {
                                            try {
                                                val createClient =
                                                    RetrofitInstance.clientService.createClient(
                                                        client
                                                    )
                                                if (createClient.isSuccessful) {
                                                    Log.d("Mediaflix", "Cliente creado con éxito")
                                                } else {
                                                    Log.d(
                                                        "Mediaflix",
                                                        "Error: ${createClient.code()} - ${createClient.message()}"
                                                    )
                                                }
                                            } catch (e: Exception) {
                                                Log.d("Mediaflix", "Exception: ${e.message}")
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.d(
                                        "Mediaflix",
                                        "Exception al hacer el getByEmail: ${e.message}"
                                    )
                                }
                            }
                            */

                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Login con google",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(40.dp)
                    )

                    Text(text = "Login con Google",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )


                    /*
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
                    */

                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}





