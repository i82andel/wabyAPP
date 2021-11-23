package com.racoon.waby.ui.viewmodel.auth.login

import androidx.annotation.IntegerRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.common.Resource
import com.racoon.waby.common.SingleLiveEvent
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LoginViewModel(private val authUserUseCase: AuthUserUseCase) : ViewModel() {

    private val signUpSLE = SingleLiveEvent<Unit>()
    private val errorSLE = SingleLiveEvent<Int>()
    private val successSLE = SingleLiveEvent<Int>()
    val errorLD: LiveData<Int> = errorSLE
    val successLD: LiveData<Int> = successSLE
    val signUpLD: LiveData<Unit> = signUpSLE

    fun onSignUpPressed() {
        signUpSLE.call()
    }

    fun login(email: String, passwd: String) {
        if (email.isEmpty()) {
            errorSLE.value = R.string.login_error_email
            return
        }
        if (passwd.isEmpty()) {
            errorSLE.value = R.string.login_error_passwd
            return
        }

        Firebase.auth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener { authResult->
            if (authResult.isSuccessful) {
                val user = Firebase.auth.currentUser
                if (user != null && user.isEmailVerified) {
                    successSLE.value = R.string.login_success
                }else {
                    errorSLE.value = R.string.login_email
                }

            }else {
                errorSLE.value = R.string.login_error
                println("No se ha podido loggear")
            }
        }

    }
}



