package com.mouredev.aristidevslogin.ui.signup.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mouredev.aristidevslogin.R
import com.mouredev.aristidevslogin.components.CButton
import com.mouredev.aristidevslogin.components.CPasswordTextField
import com.mouredev.aristidevslogin.components.CTextField
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpFormEvent
import com.mouredev.aristidevslogin.ui.signup.ui.SignUpViewModel
import com.mouredev.aristidevslogin.ui.theme.AlegreyaFontFamily
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily


@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        color = Color(204, 173, 228),
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
                            viewModel.onEvent(SignUpFormEvent.FirstNameChanged(it))
                        },
                        value = viewModel.state.firstName,
                        errorMessage = viewModel.state.firstNameError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // LastName
                    CTextField(
                        hint = "Apellidos",
                        onValueChange = {
                            viewModel.onEvent(SignUpFormEvent.LastNameChanged(it))
                        },
                        value = viewModel.state.lastName,
                        errorMessage = viewModel.state.lastNameError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Email
                    CTextField(
                        hint = "Email",
                        onValueChange = {
                            viewModel.onEvent(SignUpFormEvent.EmailChanged(it))
                        },
                        value = viewModel.state.email,
                        errorMessage = viewModel.state.emailError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Password
                    CPasswordTextField(
                        hint = "Contraseña",
                        value = viewModel.state.password,
                        onValueChange = { viewModel.onEvent(SignUpFormEvent.PasswordChanged(it)) },
                        errorMessage = viewModel.state.passwordError,
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

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

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón registro
                    CButton(
                        onSignUpClick,
                        text = "Registrarse"
                    )
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
                                color = Color.White
                            ),
                            modifier = Modifier.clickable { onLoginClick }

                        )
                    }
                }
            }
        }
    }
}


