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
    private val _state: MutableState<SpotListState> = mutableStateOf(SpotListState())

    fun getSimilarSpotList(){
        
    }
    fun getSpotList(idSpot: String):LiveData<MutableList<Spot>> {
        val mutableData = MutableLiveData<MutableList<Spot>>()
        spotRepository.getAllSpots(idSpot).observeForever{ spotList ->
            mutableData.value = spotList
        }
        return mutableData
    }

    suspend fun addRatingToSpot(rating: Float, idSpot: String) {
        val thisSpot = spotRepository.getSingleSpot(idSpot)
        thisSpot.rating?.add(rating.toInt())
        spotRepository.addRatingToSpot(thisSpot)
    }

    suspend fun getThisSpot(idSpot: String) :Spot{
        return spotRepository.getSingleSpot(idSpot)
    }

    suspend fun addAssistant(idSpot: String, idUser: String) {
        spotRepository.addAsistant(idSpot, idUser)
    }
}