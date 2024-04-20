package com.mouredev.aristidevslogin.ui.login.ui

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


class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Los campos que vienen de la UI al VM los hacemos mutableStateOf
    var loginData by mutableStateOf(LoginData())
        private set

    fun onLoginChanged(email: String, password: String) {
        Log.d(TAG_LOG,"onLoginChanged")
        loginData = loginData.copy(email = email)
        loginData = loginData.copy(password = password)

        var areFieldsValid =  isValidEmail(email) && isValidPassword(password)

        _uiState.update { currentState -> currentState.copy(loginEnable = areFieldsValid) }
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    suspend fun onLoginSelected() {
        Log.d(TAG_LOG,"OnLoginSelected")
        _uiState.update { currentState -> currentState.copy(isLoading = true) }

        //El delay genera recompose...
       // delay(4000)

        var loginMessage = "Login Ok"
        if (!isValidLogin()) {
            loginMessage = "Login incorrecto, revise las credenciales"
        }
        _uiState.update { currentState -> currentState.copy(loginMessage = loginMessage) }
        _uiState.update { currentState -> currentState.copy(isLoading = false)}
        //Se hará recompose al haber cambiado el loginData. Cambiamos el valor de loginChecked para mostrar el toast
        loginData = loginData.copy(password = "", email="", loginChecked = true)

    }

    fun onToastShowed() {
        //Una vez lanzado el toast quitamos el flag para mostrarlo
        loginData = loginData.copy(loginChecked = false)
    }

    private fun isValidLogin():Boolean
    {
        var isValidLogin : Boolean = false
        isValidLogin=(loginData.email == "oscar@gmail.com") && (loginData.password == "1234")
        return isValidLogin
    }
}

