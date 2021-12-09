package com.racoon.waby.domain.usecases.spotAdmin

import android.location.Location
import com.racoon.waby.data.model.*
import com.racoon.waby.data.repository.SpotRepository
import com.racoon.waby.data.repository.UserRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class addSpotUseCase @Inject constructor(private val spotRepository: SpotRepository) {

    fun addNewSpot(
        name: String,
        adminUser: AdminUser,
        phoneNumber: String,
        capacity: Int,
        location: Location,
        rating: Float,
        website: String,
        spotType: SpotType,
        badges: ArrayList<Badge>,
        images: ArrayList<Image>,
        description: String
    ) {

        /*val spot = Spot(
            idSpot = UUID.randomUUID().toString(),
            name = name,
            adminUser = adminUser,
            phoneNumber = phoneNumber,
            capacity = capacity,
            location = location,
            rating = rating,
            website = website,
            spotType = spotType,
            badges = badges,
            images = images,
            description = description
        )

        spotRepository.addNewSpot(spot)
*/
    }


}