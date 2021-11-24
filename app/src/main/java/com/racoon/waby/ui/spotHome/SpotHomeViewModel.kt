package com.racoon.waby.ui.spotHome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.racoon.waby.common.Result
import com.racoon.waby.data.repository.SpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SpotHomeViewModel @Inject constructor(private val spotRepository: SpotRepository): ViewModel() {

    private val _state: MutableState<SpotListState> = mutableStateOf(SpotListState())
    val state : State<SpotListState>
        get() = _state

    init {
        getSpotList()
    }

    fun getSpotList() {
        spotRepository.getSpotList().onEach { result ->
            when(result){
                is Result.Error -> {
                    _state.value = SpotListState(error = result.message?: "Error inesperado")
                }
                is Result.Loading -> {
                    _state.value = SpotListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = SpotListState(spots = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}