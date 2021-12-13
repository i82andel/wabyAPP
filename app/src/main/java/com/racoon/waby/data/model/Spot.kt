package com.racoon.waby.data.model

import android.location.Location

data class Spot (val idSpot: String,
                 val name: String? = null,
                 val adminUser: String? = null,
                 val phoneNumber: String? = null,
                 var capacity: Int? = null,
                 val location: Location? = null,
                 var rating: ArrayList<Int>? = null,
                 val website: String? = null,
                 val spotType: String? = null,
                 val badges: ArrayList<String>? = null,
                 val images: ArrayList<String>? = null,
                 val description: String? = null,
                 val assistants : ArrayList<String>? = null,
)

enum class SpotType {
    CAMPUS,
    DISCOTECA,
    PUB
}