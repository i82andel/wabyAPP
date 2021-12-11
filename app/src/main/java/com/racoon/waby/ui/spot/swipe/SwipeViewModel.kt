package com.racoon.waby.ui.spot.swipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.data.repository.WabiRepository
import com.racoon.waby.ui.spot.wabis.UserListState

class SwipeViewModel : ViewModel() {

    private var spotRepository = SpotRepository()
    private var wabiRepository = WabiRepository()


    private val _state: MutableState<UserListState> = mutableStateOf(UserListState())

    suspend fun getUsersFromSpot(): MutableList<User> {
        lateinit var mutableData : MutableList<User>
        mutableData = wabiRepository.getUsersFromList(getSwipeList())
        println("mutable data: ${mutableData}")
        return mutableData
    }

    suspend fun getSwipeList(): ArrayList<String>? {
        val spot = spotRepository.getSingleSpot("zdzillZYB1nVzTpak2Lz")
        println("asistentes ${spot.assistants}")
        return spot.assistants

    }
}