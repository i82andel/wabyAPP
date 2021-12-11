package com.racoon.waby.ui.spot.swipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.data.repository.WabiRepository
import com.racoon.waby.ui.spot.wabis.UserListState
import kotlinx.coroutines.Dispatchers

class SwipeViewModel : ViewModel() {

    private var spotRepository = SpotRepository()
    private var wabiRepository = WabiRepository()


    private val _state: MutableState<UserListState> = mutableStateOf(UserListState())

    suspend fun getUsersFromSpot(): LiveData<MutableList<User>> {
        val mutableData = MutableLiveData<MutableList<User>>()
        wabiRepository.getUsersFromList(getSwipeList()).observeForever{ wabiList ->
            mutableData.value = wabiList
        }

        println("mutable data: ${mutableData.value.toString()}")
        return mutableData
    }


    suspend fun getSwipeList(): ArrayList<String>? {
        val spot = spotRepository.getSingleSpot("zdzillZYB1nVzTpak2Lz")
        println("asistentes ${spot.assistants}")
        return spot.assistants

    }

}