package com.mouredev.aristidevslogin.ui.signup.ui

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.mouredev.aristidevslogin.User
import com.mouredev.aristidevslogin.validations.ValidateEmail
import com.mouredev.aristidevslogin.validations.ValidateName
import com.mouredev.aristidevslogin.validations.ValidatePassword
import com.mouredev.aristidevslogin.validations.ValidateRepeatedPassword

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class SignUpViewModel(
    private val validateFirstName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
) : ViewModel() {

    var state by mutableStateOf(SignUpFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val auth: FirebaseAuth = Firebase.auth

    fun onEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }

            is SignUpFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }

            is SignUpFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is SignUpFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }

            is SignUpFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Mediaflix", "Logueado exitoso con Google")
                            home()
                        }
                    }.addOnFailureListener {
                        Log.d("Mediaflix", "Error al loguear con Google")
                    }
            } catch (ex: Exception) {
                Log.d("Mediaflix", "Excepción al loguear con Google ${ex.localizedMessage}")
            }
        }

    private fun submitData() {
        val firstNameResult = validateFirstName.execute(state.firstName)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )

        val hasError = listOf(
            firstNameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                firstNameError = firstNameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }


    @SuppressLint("NewApi")
    fun createUserWithEmailAndPassword(email: String, password: String, firstName: String, lastName:String, home: () -> Unit) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val displayName =
                    task.result.user?.email?.split("@")?.get(0)
                createUser(firstName, lastName)
                home()
            } else {
                Log.d("Mediaflix", "createUserWithEmailAndPassword: ${task.result.toString()}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createUser(firstName: String, lastName: String) {

        val userId = auth.currentUser?.uid
        // val user = mutableMapOf<String, Any>()

        // Usando un data class
        val user = User(
            userId = userId.toString(),
            firstName = firstName,
            lastName = lastName,
            creationDate = Date(),
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("Mediaflix", "Creado ${it.id}")
            }.addOnFailureListener{
                Log.d("Mediaflix", "Ocurrió un error")
            }

    }

}