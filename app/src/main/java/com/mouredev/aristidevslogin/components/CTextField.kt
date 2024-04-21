package com.mouredev.aristidevslogin.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mouredev.aristidevslogin.ui.theme.AlegreyaSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CTextFieldWithError(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String = "",
    isEnabled: Boolean,
    isValid: Boolean,
) {
    var isError by rememberSaveable { mutableStateOf(isValid) } // Track error state
    Column(
        modifier = Modifier.fillMaxWidth() // Ensure full width for error message placement
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = hint,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = AlegreyaSansFontFamily,
                        color = Color.Black,
                    )
                )
            },
            label = {
                Text(
                    text = hint,
                    color = if (isError) Color.Red else Color.Black,
                )
            },
            supportingText = {
                if (!isValid && value.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red
                    )
                }
            },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),


            colors = TextFieldDefaults.textFieldColors(
                containerColor = if (isError) Color.Red else Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                textColor = Color.Black
            ),

            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CTextField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit = {},
    errorMessage: String?
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        isError = errorMessage != null,
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(text = hint)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            textColor = Color.Black
        )
    )
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CPasswordTextField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit = {},
    errorMessage: String?,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        isError = errorMessage != null,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = hint)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            textColor = Color.Black
        ),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(
                onClick = passwordVisibleChange
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = "Ver contraseña"
                )
            }
        },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
    )
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CPasswordTextField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),

        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        label = { Text(text = hint) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(
                onClick = passwordVisibleChange
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = "Ver contraseña"
                )
            }
        },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        colors = if (isValidPassword) {
            TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color.Green,
                focusedBorderColor = Color.Green,
                containerColor = Color.White
            )
        } else {
            TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color.Red,
                focusedBorderColor = Color.Red,
                containerColor = Color.White
            )
        }


    )
}