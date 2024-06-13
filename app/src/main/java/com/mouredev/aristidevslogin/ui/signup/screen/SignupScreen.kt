package com.mouredev.aristidevslogin.ui.signup.screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.components.CButton
import com.mouredev.aristidevslogin.components.CPasswordTextField
import com.mouredev.aristidevslogin.components.CTextField
import com.mouredev.aristidevslogin.data.RetrofitInstance
import com.mouredev.aristidevslogin.data.model.Client
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpFormEvent
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpViewModel
import com.mouredev.aristidevslogin.ui.theme.AlegreyaFontFamily
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily
import com.stripe.exception.ApiException
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale


@SuppressLint("NewApi")
@Composable
fun SignUpScreen(
    //viewModelClient: ClientViewModel,
    viewModelSignUp: SignUpViewModel,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
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
            viewModelSignUp.signInWithGoogleCredential(credential) {
                // Create client into API
                val userEmail = FirebaseAuth.getInstance().currentUser?.email
                val fullname = FirebaseAuth.getInstance().currentUser?.displayName
                val parts = fullname?.split("\\s+".toRegex())
                val firstname = parts?.get(0)
                val lastname = parts?.get(1)

                val lastName = FirebaseAuth.getInstance().currentUser?.displayName

                if (userEmail != null) {
                    scope.launch {
                        insertClientInApi(userEmail, firstname, lastname)
                        onSignUpClick()
                    }
                } else {
                    Toast.makeText(context, "Fallo al obtener el email del usuario", Toast.LENGTH_SHORT).show()
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

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                item {
                    Row(modifier = Modifier.align(Alignment.CenterStart)) {
                        Column {
                            // Logo
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 54.dp)
                                    .height(100.dp)
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
                            )

                            Text(
                                "Registrate ahora para acceder a un portal de diversión sin límites",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = AlegreyaSansFontFamily,
                                    color = Color(0xB2FFFFFF)
                                ),
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                            )
                        }
                    }
                }


                item {

                    // FirstName
                    CTextField(
                        hint = "Nombre",
                        onValueChange = {
                            viewModelSignUp.onEvent(SignUpFormEvent.FirstNameChanged(it))
                        },
                        value = viewModelSignUp.state.firstName,
                        errorMessage = viewModelSignUp.state.firstNameError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // LastName
                    CTextField(
                        hint = "Apellidos",
                        onValueChange = {
                            viewModelSignUp.onEvent(SignUpFormEvent.LastNameChanged(it))
                        },
                        value = viewModelSignUp.state.lastName,
                        errorMessage = viewModelSignUp.state.lastNameError
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    // Email
                    CTextField(
                        hint = "Email",
                        onValueChange = {
                            viewModelSignUp.onEvent(SignUpFormEvent.EmailChanged(it))
                        },
                        value = viewModelSignUp.state.email,
                        errorMessage = viewModelSignUp.state.emailError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Password
                    CPasswordTextField(
                        hint = "Contraseña",
                        value = viewModelSignUp.state.password,
                        onValueChange = { viewModelSignUp.onEvent(SignUpFormEvent.PasswordChanged(it)) },
                        errorMessage = viewModelSignUp.state.passwordError,
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    /*
                                        // Repeat Password
                                        CPasswordTextField(
                                            hint = "Confirmar contraseña",
                                            value = viewModel.state.repeatedPassword,
                                            onValueChange = {
                                                viewModel.onEvent(
                                                    SignUpFormEvent.RepeatedPasswordChanged(
                                                        it
                                                    )
                                                )
                                            },
                                            errorMessage = viewModel.state.repeatedPasswordError,
                                            passwordVisible = passwordVisible,
                                            passwordVisibleChange = { passwordVisible = !passwordVisible },
                                        )
                    */

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón registro
                    CButton(
                        onClick = {

                            scope.launch {
                                try {
                                    val response =
                                        RetrofitInstance.clientService.searchClientByEmail(
                                            viewModelSignUp.state.email
                                        )
                                    if (response.isSuccessful) { // Ya existe un cliente con ese email
                                        Log.d("Mediaflix", "Ya existe un cliente con ese email")
                                        Toast.makeText(
                                            context,
                                            "Ya existe un cliente con ese email. Por favor intenéntelo de nuevo con otro email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        //return@launch
                                    } else {
                                        viewModelSignUp.createUserWithEmailAndPassword(
                                            viewModelSignUp.state.email,
                                            viewModelSignUp.state.password,
                                            viewModelSignUp.state.firstName,
                                            viewModelSignUp.state.lastName
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
                                            firstName = viewModelSignUp.state.firstName,
                                            lastName = viewModelSignUp.state.lastName,
                                            email = viewModelSignUp.state.email,
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


                            /*
                            viewModelClient.searchClientByEmail(viewModelSignUp.state.email)

                            var client = viewModelClient.searchClientResult.value?.data

                            if (client != null) {
                                Toast.makeText(
                                    context, "Ya existe un cliente con ese email", Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                Toast.makeText(
                                    context, "No existe ningun cliente con ese email", Toast.LENGTH_SHORT
                                ).show()
                                /*viewModelSignUp.createUserWithEmailAndPassword(viewModelSignUp.state.email,viewModelSignUp.state.password,viewModelSignUp.state.firstName, viewModelSignUp.state.lastName){
                                    onSignUpClick()
                                }*/
                            }
                            */
                            /*
                            // Funciona
                            viewModelSignUp.createUserWithEmailAndPassword(viewModelSignUp.state.email,viewModelSignUp.state.password,viewModelSignUp.state.firstName, viewModelSignUp.state.lastName) {
                                onSignUpClick()
                            }
                            */


                        },
                        text = "Registrarse"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

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
                                                viewModelSignUp.state.email
                                            )
                                        if (response.isSuccessful) { // Ya existe un cliente con ese email

                                            Log.d("Mediaflix", "Ya existe un cliente con ese email")
                                            Toast.makeText(
                                                context,
                                                "Ya existe un cliente con ese email. Por favor intenéntelo de nuevo con otro email",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        } else {

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
                                }*/


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

                        Text(text = "Registrarse con Google",
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
                }
                item {
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
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.clickable { onLoginClick() }

                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
suspend fun insertClientInApi(email: String, firstname: String?, lastname: String?) {

    val now = Instant.now()
    val sqlFormattedDate = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",
        Locale.getDefault()
    ).format(Date(now.toEpochMilli()))

    val client = Client(
        clientId = 0,
        firstName = firstname!!,
        lastName = lastname!!,
        email = email,
        creationDate = sqlFormattedDate,
        available = true
    )
    try {
        val createClient = RetrofitInstance.clientService.createClient(client)
        if (createClient.isSuccessful) {
            Log.d("Mediaflix", "Cliente creado con éxito")
        } else {
            Log.d("Mediaflix", "Error: ${createClient.code()} - ${createClient.message()}")
        }
    } catch (e: Exception) {
        Log.d("Mediaflix", "Exception: ${e.message}")
    }
}


