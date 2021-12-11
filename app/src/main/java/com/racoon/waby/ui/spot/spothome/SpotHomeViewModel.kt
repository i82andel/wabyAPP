package com.racoon.waby.ui.spot.spothome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.racoon.waby.common.Result
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.repository.SpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


class SpotHomeViewModel : ViewModel() {

    private val spotRepository = SpotRepository()
    private var thisSpot = Spot("spot")
    private val _state: MutableState<SpotListState> = mutableStateOf(SpotListState())


    init {
        GlobalScope.launch (Dispatchers.Main){
            thisSpot = spotRepository.getSingleSpot("zdzillZYB1nVzTpak2Lz")
            getSpotList()
        }

    }

    fun getSpotList():LiveData<MutableList<Spot>> {
        val mutableData = MutableLiveData<MutableList<Spot>>()
        spotRepository.getAllSpots().observeForever{ spotList ->
            mutableData.value = spotList
        }
        return mutableData
    }

    suspend fun addRatingToSpot(rating: Float) {
        thisSpot.rating?.add(rating.toInt())
        spotRepository.addRatingToSpot(thisSpot)

    }

    suspend fun getThisSpot() :Spot{
        return spotRepository.getSingleSpot("zdzillZYB1nVzTpak2Lz")
    }
}