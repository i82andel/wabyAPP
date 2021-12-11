package com.racoon.waby.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.data.model.Badge
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class SpotRepository{

    private val fireste = Firebase.firestore
    private val spotList = fireste.collection("Spot")

    fun getAllSpots():LiveData<MutableList<Spot>>{
        val mutableList = MutableLiveData<MutableList<Spot>>()
        spotList.get().addOnSuccessListener {
            val spotDataList = mutableListOf<Spot>()
            for (document in it){
                val spot = documentToSpot(document)
                spotDataList.add(spot)
            }
            mutableList.value = spotDataList
        }
        Log.d("creation", "$mutableList")
        return mutableList
    }

    suspend fun getSingleSpot(idSpot: String): Spot {

        var spot = Spot("not_found")
        val job1 = spotList.document(idSpot)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    println("entra")
                    spot = documentToSpot(document)
                }
            }
        job1.await()
        return spot
    }

    private fun documentToSpot(document : DocumentSnapshot) : Spot{

        val adminUser = document.getString("adminUser")
        val badges = document.data?.get("badges")
        val capacity = document.getLong("capacity")?.toInt()
        val description = document.get("description").toString()
        val images = document.data?.get("images")
        //val location = document.getLong("location")
        val name = document.get("name").toString()
        val phoneNumber = document.get("phoneNumber").toString()
        val spotType = document.getString("spotType")
        val website = document.getString("website")
        val ratings = document.data?.get("ratings")
        val assistants = document.data?.get("assistants")

        val spot = Spot(
            "idSpot",
            name,
            adminUser,
            phoneNumber,
            capacity,
            null,
            ratings as ArrayList<Int>?,
            website,
            spotType,
            badges as ArrayList<String>?,
            images as ArrayList<String>?,
            description,
            assistants as ArrayList<String>?
        )

        return spot
    }

    fun getSimilarSpots(badge: String):LiveData<MutableList<Spot>> {
        val mutableList = MutableLiveData<MutableList<Spot>>()
        spotList.whereArrayContains("badges", badge).get().addOnSuccessListener {
            val spotDataList = mutableListOf<Spot>()
            for (document in it) {
                val spot = documentToSpot(document)
                spotDataList.add(spot)
            }
            mutableList.value = spotDataList
        }
        Log.d("creation", "$mutableList")
        return mutableList
    }

}
