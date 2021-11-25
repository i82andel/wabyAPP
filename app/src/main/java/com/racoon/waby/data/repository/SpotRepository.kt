package com.racoon.waby.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.GeoPoint
import com.racoon.waby.common.Result
import com.racoon.waby.data.model.Spot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotRepository @Inject constructor(private val spotList: CollectionReference) {

    fun addNewSpot(spot: Spot) {
        try {
            spotList.document(spot.idSpot).set(spot)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSpotList(): Flow<Result<List<Spot>>> = flow {
        try {
            emit(Result.Loading<List<Spot>>())

            val spotList = spotList.get().await().map { documents ->
                documents.toObject(Spot::class.java)
            }

            emit(Result.Success<List<Spot>>(data = spotList))

        } catch (e: Exception) {
            emit(Result.Error<List<Spot>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun getSingleSpot(idSpot: String): Spot {

        var spot: Spot
        val docRef = spotList.document(idSpot)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {

                    document.toObject(Spot)

                    val adminUser = document.get("adminUser").toString()
                    val badges = document.get("badges") to Array<String>()
                    val capacity = document.get("capacity") to Int
                    val description = document.get("description").toString()
                    val images = document.get("images") to Array<String>
                    val location = document.get("location") to GeoPoint
                    val name = document.get("name").toString()
                    val phoneNumber = document.get("phoneNumber").toString()
                    val spotType = document.get("spotType").toString()
                    val website = document.get("website").toString()
                    val ratings = document.get("ratings") to Array<Int>

                    val spot = Spot(
                        idSpot,
                        name,
                        adminUser,
                        phoneNumber,
                        capacity,
                        location,
                        ratings,
                        website,
                        spotType,
                        badges,
                        images,
                        description
                    )

                }
            }
    }


}