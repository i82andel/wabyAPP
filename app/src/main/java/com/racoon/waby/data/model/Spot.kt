package com.racoon.waby.data.model

import android.location.Location

data class Spot (val idSpot: String,
                 val name: String,
                 val adminUser: String,
                 val phoneNumber: String,
                 var capacity: Int,
                 val location: Location,
                 var ratings: Array<Int>,
                 val website: String,
                 val spotType: SpotType,
                 val badges: ArrayList<Badge>,
                 val images: ArrayList<Image>,
                 val description: String
)

enum class SpotType {
    CAMPUS,
    DISCOTECA,
    PUB
}