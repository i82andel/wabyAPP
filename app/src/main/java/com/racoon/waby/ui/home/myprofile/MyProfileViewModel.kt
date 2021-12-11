package com.racoon.waby.ui.home.myprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.Gender
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.data.repository.UserRepository
import com.racoon.waby.data.repository.WabiRepository
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCase
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import java.util.*

class MyProfileViewModel() : ViewModel()  {

    private val wabiRepository = WabiRepository()

    suspend fun getCurrentUser(): User {
        val uid = Firebase.auth.currentUser?.uid as String
        return wabiRepository.getSingleUser(uid)
    }


}