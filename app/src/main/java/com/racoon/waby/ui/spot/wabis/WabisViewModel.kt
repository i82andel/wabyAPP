package com.racoon.waby.ui.spot.wabis

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.WabiRepository
import com.racoon.waby.ui.spot.spothome.SpotListState

class WabisViewModel : ViewModel() {

    private var wabiRepository = WabiRepository()

    private val _state: MutableState<UserListState> = mutableStateOf(UserListState())


    fun getAllUsers(): LiveData<MutableList<User>>{
        val mutableData = MutableLiveData<MutableList<User>>()
        wabiRepository.getAllUsers().observeForever{ wabiList ->
            mutableData.value = wabiList
        }
        return mutableData

    }
    suspend fun getMatchList():LiveData<MutableList<User>> {
        val mutableData = MutableLiveData<MutableList<User>>()
        wabiRepository.getMatchesFromUser(getUser()).observeForever{ wabiList ->
            mutableData.value = wabiList
        }
        return mutableData
    }

    suspend fun getWabisList():LiveData<MutableList<User>> {
        val mutableData = MutableLiveData<MutableList<User>>()
        wabiRepository.getWabisFromUser(getUser()).observeForever{ wabiList ->
            mutableData.value = wabiList
        }
        return mutableData
    }

    suspend fun getUser(): User{
        val uid = Firebase.auth.currentUser?.uid as String
        return wabiRepository.getSingleUser(uid)
    }
}