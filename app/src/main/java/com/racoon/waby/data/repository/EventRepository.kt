package com.racoon.waby.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.local.ReferenceSet
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.Event
import java.util.*

import javax.inject.Singleton
import kotlin.collections.ArrayList


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

        lateinit var event : Event
        val docRef = EventList.document(idSpot)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {

                    event = documentToEvent(document)

                }
            }
        return event
    }

    private fun documentToEvent(document : DocumentSnapshot) : Event {

        val assistants = document.data?.get("asistentes")
        val attendances = document.data?.get("attendance")
        val eventLog = document.data?.get("eventLog")
        val fechaEvento = document.get("fechaEvento")
        val idSpot = document.getString("idSpot")

        val event  = Event("found",
            assistants as ArrayList<String>,
            attendances as ArrayList<String>,
            eventLog as ReferenceSet,
            fechaEvento as Date,
            idSpot!!)

        return event

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