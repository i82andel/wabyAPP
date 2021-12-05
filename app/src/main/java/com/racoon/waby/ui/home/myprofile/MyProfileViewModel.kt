package com.racoon.waby.ui.home.myprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.UserRepository
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCase
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl

class MyProfileViewModel(private val authUserUseCase: AuthUserUseCase) : ViewModel()  {

    init {
        getCurrentUser()
    }

    fun getCurrentUser() : User {

       return authUserUseCase.firebaseDefaultGetCurrentUser()
    }
}