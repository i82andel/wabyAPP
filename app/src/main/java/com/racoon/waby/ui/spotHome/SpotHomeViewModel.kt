package com.racoon.waby.ui.spotHome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.racoon.waby.common.Result
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.repository.SpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SpotHomeViewModel @Inject constructor(private val spotRepository: SpotRepository): ViewModel() {

    private lateinit var spotRecyclerView: RecyclerView

    private val _state: MutableState<SpotListState> = mutableStateOf(SpotListState())
    val state : State<SpotListState>
        get() = _state

    init {

    }

    fun getSpotList() {
        spotRecyclerView =
    }


}