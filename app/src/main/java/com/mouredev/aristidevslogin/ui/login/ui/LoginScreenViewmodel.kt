package com.mouredev.aristidevslogin.ui.login.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginScreenViewmodel : ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email : String, password:String,home:()-> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Log.d("Mediaflix", "signInWithEmailAndPassword logueado!")
                        home()
                    }else{
                        Log.d("Mediaflix", "signInWithEmailAndPassword ${task.result.toString()}")

                    }

                }


        }catch (ex:Exception){
            Log.d("Mediaflix", "signInWithEmailAndPassword ${ex.message}")

        }
    }


    /*
    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit){

        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        home()
                    }else{
                        Log.d("Mediaflix", "createUserWithEmailAndPassword: ${task.result}")
                    }
                    _loading.value = false
                }
        }

    }
    */


}