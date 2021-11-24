package com.racoon.waby.ui.spotHome

import com.racoon.waby.data.model.Spot

data class SpotListState (
    val isLoading: Boolean  = false,
    val spots : List<Spot>? = emptyList(),
    val error: String = ""
)