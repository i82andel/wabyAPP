package com.racoon.waby.data.model

import android.location.Location

data class Spot (val idSpot: String,
                 val name: String? = null,
                 val adminUser: String? = null,
                 val phoneNumber: String? = null,
                 var capacity: Int? = null,
                 val location: Location? = null,
                 var rating: Float? = null,
                 val website: String? = null,
                 val spotType: SpotType? = null,
                 val badges: ArrayList<Badge>? = null,
                 val images: ArrayList<Image>? = null,
                 val description: String? = null
)

enum class SpotType {
    CAMPUS,
    DISCOTECA,
    PUB
}