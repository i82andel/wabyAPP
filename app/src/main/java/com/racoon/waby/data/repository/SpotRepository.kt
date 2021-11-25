package com.racoon.waby.data.repository

import com.google.firebase.firestore.CollectionReference
import com.racoon.waby.common.Result
import com.racoon.waby.data.model.Spot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotRepository @Inject constructor(private val spotList: CollectionReference){

    fun addNewSpot(spot: Spot) {
        try {
            spotList.document(spot.idSpot).set(spot)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun getSpotList() : Flow<Result<List<Spot>>> = flow {
        try {
            emit(Result.Loading<List<Spot>>())

            val spotList = spotList.get().await().map { documents ->
                documents.toObject(Spot::class.java)
            }

            emit(Result.Success<List<Spot>>(data = spotList))

        }catch (e: Exception){
            emit(Result.Error<List<Spot>>(message = e.localizedMessage?: "Error Desconocido"))
        }
    }


}