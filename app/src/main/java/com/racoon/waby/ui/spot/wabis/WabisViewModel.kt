package com.racoon.waby.ui.spot.wabis

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.WabiRepository
import com.racoon.waby.ui.spot.spothome.SpotListState

class WabisViewModel : ViewModel() {

    private var wabiRepository = WabiRepository()

    private val _state: MutableState<UserListState> = mutableStateOf(UserListState())


    init {
        getAllUsers()
    }

    fun getAllUsers(): LiveData<MutableList<User>>{
        val mutableData = MutableLiveData<MutableList<User>>()
        wabiRepository.getAllUsers().observeForever{ wabiList ->
            mutableData.value = wabiList
        }
        return mutableData

    }
    fun getWabisList():LiveData<MutableList<User>> {
        val mutableData = MutableLiveData<MutableList<User>>()
        wabiRepository.getWabisFromUser(getUser()).observeForever{ wabiList ->
            mutableData.value = wabiList
        }
        return mutableData
    }

    fun getUser(): User{
        return wabiRepository.getSingleUser("1q8YgG2CAPTajk0218aeeC8X3Hj2")
    }
}