package com.racoon.waby.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.*
import java.util.*
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class WabiRepository {

    private val fireste = Firebase.firestore
    private val EventList = fireste.collection("Event")

    fun getAllEventsBySpot(spot : String): LiveData<MutableList<User>> {
        val mutableList = MutableLiveData<MutableList<User>>()
        EventList.get().addOnSuccessListener {
            val DataList = mutableListOf<User>()
            for (document in it){
                val user = documentToUser(document)
                DataList.add(user)
            }
            mutableList.value = DataList
        }
        Log.d("creation", "$mutableList")
        return mutableList
    }

    fun getSingleSpot(idSpot: String): User {

        lateinit var user: User
        val docRef = EventList.document(idSpot)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {

                    user = documentToUser(document)
                }
            }
        return user
    }

    private fun documentToUser(document: DocumentSnapshot): User {

        val idUser = document.id
        val name = document.getString("name")
        val surname = document.getString("surname")
        val userName = document.getString("userName")
        val birthdate = document.data?.get("birthdate")
        val email = document.getString("email")
        val gender = document.getString("gender")
        val phoneNumber = document.getString("phoneNumber")
        val description = document.getString("description")
        val tags = document.data?.get("tags")
        val images = document.data?.get("images")
        val wabis = document.data?.get("wabis")

        val user = User(
            idUser,
            name,
            surname,
            userName,
            birthdate as Date?,
            email,
            gender,
            phoneNumber,
            description,
            tags as ArrayList<String>?,
            images as ArrayList<String>?,
            wabis as ArrayList<String>
        )

        return user
    }

    fun getSpotByDate(date: Date): LiveData<MutableList<User>> {
        val mutableList = MutableLiveData<MutableList<User>>()
        EventList.whereArrayContains("fechaEvento", date).get().addOnSuccessListener {
            val spotDataList = mutableListOf<User>()
            for (document in it) {
                val user = documentToUser(document)
                spotDataList.add(user)
            }
            mutableList.value = spotDataList
        }
        Log.d("creation", "$mutableList")
        return mutableList
    }

}