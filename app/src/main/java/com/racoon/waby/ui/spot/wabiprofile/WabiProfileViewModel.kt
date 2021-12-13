package com.racoon.waby.ui.spot.wabiprofile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.WabiRepository

class WabiProfileViewModel() : ViewModel()  {

    private val wabiRepository = WabiRepository()

    suspend fun getUser(uid: String): User {
        return wabiRepository.getSingleUser(uid)
    }




}