package com.racoon.waby.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.Event

import javax.inject.Singleton


@Singleton
class EventRepository {
    private val fireste = Firebase.firestore
    private val EventList = fireste.collection("Event")



    fun getAllEventsBySpot(spot : String): LiveData<MutableList<Event>> {
        val mutableList = MutableLiveData<MutableList<Event>>()
        EventList.get().addOnSuccessListener {
            val spotDataList = mutableListOf<Event>()
            for (document in it){
                val spot = documentToEvent(document)
                spotDataList.add(spot)
            }
            mutableList.value = spotDataList
        }
        Log.d("creation", "$mutableList")
        return mutableList
    }

    fun getSingleSpot(idSpot: String): Event {

        var spot= Event("not_found")
        EventList
        val docRef = EventList.document(idSpot)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {

                    val spot = documentToEvent(document)

                }
            }
        return spot
    }

    private fun documentToEvent(document : DocumentSnapshot) : Event {



    }

    fun getSimilarSpots(badge: String): LiveData<MutableList<Event>> {
        val mutableList = MutableLiveData<MutableList<Event>>()
        EventList.whereArrayContains("badges", badge).get().addOnSuccessListener {
            val spotDataList = mutableListOf<Event>()
            for (document in it) {
                val spot = documentToEvent(document)
                spotDataList.add(spot)
            }
            mutableList.value = spotDataList
        }
        Log.d("creation", "$mutableList")
        return mutableList
    }
}