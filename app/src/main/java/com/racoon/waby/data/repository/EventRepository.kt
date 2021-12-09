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
}