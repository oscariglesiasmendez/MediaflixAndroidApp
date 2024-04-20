package com.mouredev.aristidevslogin.ui.signup.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mouredev.aristidevslogin.ui.login.screen.TAG_LOG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // Los campos que vienen de la UI al VM los hacemos mutableStateOf
    var signUpDate by mutableStateOf(SignUpData())
        private set

    fun onSignUpChanged(firstName: String, lastName: String, email: String, password: String) {
        Log.d(TAG_LOG, "onSignUpChanged")
        signUpDate = signUpDate.copy(firstName = firstName)
        signUpDate = signUpDate.copy(lastName = lastName)
        signUpDate = signUpDate.copy(email = email)
        signUpDate = signUpDate.copy(password = password)

        var areFieldsValid =
            isValidFirstName(firstName) && isValidEmail(email) && isValidPassword(password)

        _uiState.update { currentState -> currentState.copy(signUpEnable = areFieldsValid) }
    }

     fun isValidPassword(password: String): Boolean = password.length > 6

     fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

     fun isValidFirstName(firstName: String): Boolean = firstName.length >= 2


    suspend fun onSignUpSelected() {
        Log.d(TAG_LOG, "OnSignUpSelected")
        _uiState.update { currentState -> currentState.copy(isLoading = true) }

        //El delay genera recompose...
        // delay(4000)

        var signUpMessage = "SignUp Ok"

        _uiState.update { currentState -> currentState.copy(sigUpMessage = signUpMessage) }
        _uiState.update { currentState -> currentState.copy(isLoading = false) }
        //Se hará recompose al haber cambiado el signUpData. Cambiamos el valor de signUpChecked para mostrar el toast
        signUpDate = signUpDate.copy(password = "", email = "", signUpChecked = true)
    }

    fun onToastShowed() {
        //Una vez lanzado el toast quitamos el flag para mostrarlo
        signUpDate = signUpDate.copy(signUpChecked = false)
    }

}

