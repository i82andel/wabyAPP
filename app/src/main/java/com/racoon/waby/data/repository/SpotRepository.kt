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
import com.racoon.waby.data.model.Spot
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class SpotRepository{

    private val fireste = Firebase.firestore
    private val spotList = fireste.collection("Spot")
    fun addNewSpot(spot: Spot) {
        try {
            spotList.document(spot.idSpot).set(spot)
        } catch (e: Exception) {
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

    fun getSingleSpot(idSpot: String): Spot {

        var spot= Spot("not_found")
        spotList
        val docRef = spotList.document(idSpot)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {

                    val spot = documentToSpot(document)

                }
            }
        return spot
    }

    fun documentToSpot(document : DocumentSnapshot) : Spot{


        val adminUser = document.getString("adminUser")
       // val badges = document.getString("badges") to ArrayList<String>()
        val capacity = document.getLong("capacity")?.toInt()
        val description = document.get("description").toString()
        val image = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.sojoribera.com%2F&psig=AOvVaw300KUGc-fAABBRnU86Mh3j&ust=1638471612060000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPCln8Wkw_QCFQAAAAAdAAAAABAD"
        //val location = document.getString("location")
        val name = document.get("name").toString()
        val phoneNumber = document.get("phoneNumber").toString()
        val spotType = document.getString("spotType")
        val website = document.getString("website")
        //val ratings = document.get("ratings") to ArrayList<Int>()

        val images = ArrayList<String>()
        images.add(image)

        val spot = Spot(
            "idSpot",
            name,
            adminUser,
            phoneNumber,
            capacity,
            null,
            rating = null,
            website,
            null,
            null,
            images,
            description
        )

        return spot
    }

}
