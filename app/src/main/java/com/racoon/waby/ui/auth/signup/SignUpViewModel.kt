package com.racoon.waby.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.common.SingleLiveEvent
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCase

class SignUpViewModel(private val authUserUseCase: AuthUserUseCase) : ViewModel() {

    private val errorSLE = SingleLiveEvent<Int>()
    private val successSLE = SingleLiveEvent<Boolean>()
    val errorLD: LiveData<Int> = errorSLE
    val successLD: LiveData<Boolean> = successSLE
    private var taskCompleted = false

    fun create(email: String, passwd: String, repeteadPasswd: String) {
        if (email.isEmpty()) {
            errorSLE.value = R.string.signup_error_email
            return
        }
        if (passwd.isEmpty() || repeteadPasswd.isEmpty()) {
            errorSLE.value = R.string.signup_error_password
            return
        }
        if (passwd != repeteadPasswd) {
            errorSLE.value = R.string.signup_error_password_notequals
            return
        }

        Firebase.auth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener { authResult->
            if (taskCompleted) return@addOnCompleteListener
            taskCompleted = true

            val newUser = Firebase.auth.currentUser
            if (authResult.isSuccessful && newUser != null) {
                newUser.sendEmailVerification().addOnCompleteListener { emailTask->
                    successSLE.postValue(emailTask.isSuccessful)
                }
                Firebase.auth.signOut()
                successSLE.postValue(authResult.isSuccessful)
            }else {
                println("No se ha podido registrar")
                errorSLE.value = R.string.signup_error


            }
        }


    }

}