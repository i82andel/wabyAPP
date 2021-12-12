package com.racoon.waby.ui.home.main

import androidx.lifecycle.ViewModel
import com.racoon.waby.data.repository.SpotRepository

class MainHomeViewModel : ViewModel() {

    private val spotRepository = SpotRepository()

    suspend fun checkSpot(idSpot : String): Boolean {
        return spotRepository.checkSpot(idSpot)
    }

}