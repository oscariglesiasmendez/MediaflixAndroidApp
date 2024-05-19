package com.mouredev.aristidevslogin.ui.login.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.mouredev.aristidevslogin.validations.ValidateEmail
import com.mouredev.aristidevslogin.validations.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val auth: FirebaseAuth = Firebase.auth


    fun signInWithGoogleCredential(credential : AuthCredential, home:() -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        Log.d("Mediaflix", "Logueado exitoso con Google")
                        home()
                    }
                }.addOnFailureListener {
                    Log.d("Mediaflix", "Error al loguear con Google")
                }
        }catch (ex: Exception){
            Log.d("Mediaflix", "Excepción al loguear con Google ${ex.localizedMessage}")
        }
    }


    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Mediaflix", "signInWithEmailAndPassword logueado!")

                        } else {
                            Log.d(
                                "Mediaflix",
                                "signInWithEmailAndPassword ${task.result.toString()}"
                            )

                        }

                    }


            } catch (ex: Exception) {
                Log.d("Mediaflix", "signInWithEmailAndPassword ${ex.message}")

            }
        }


    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                home()
            } else {
                Log.d("Mediaflix", "createUserWithEmailAndPassword: ${task.result.toString()}")
            }
        }

    }


    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(): Boolean {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return false
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }

        return true
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}