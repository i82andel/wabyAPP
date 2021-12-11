package com.racoon.waby.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class WabiRepository {

    private val fireste = Firebase.firestore
    private val userList = fireste.collection("User")

    fun getAllUsers(): LiveData<MutableList<User>> {
        val mutableList = MutableLiveData<MutableList<User>>()
        userList.get().addOnSuccessListener {
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

    suspend fun getSingleUser(idUser: String): User {

        var user = User()
        val job1 = userList.document(idUser)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    user = documentToUser(document)
                }
            }
        job1.await()
        return user
    }

    private fun documentToUser(document: DocumentSnapshot): User {

        var images = "gs://racoonapps-cd246.appspot.com/profiles/placeholder.png"
        val idUser = document.id
        val name = document.getString("name")
        val surname = document.getString("surname")
        val userName = document.getString("username")
        val birthdate = document.data?.get("birthdate")
        val email = document.getString("email")
        val gender = document.getString("gender")
        val phoneNumber = document.getString("phoneNumber")
        val description = document.getString("description")
        val tags = document.data?.get("tags")
        if (document.getString("images")!=null) {
            images = document.getString("images")!!
        }
        val wabis = document.data?.get("wabis")
        println(wabis)

        val user = User(
            idUser,
            name,
            surname,
            userName,
            null,
            email,
            gender,
            phoneNumber,
            description,
            tags as ArrayList<String>?,
            images,
            wabis as ArrayList<String>?
        )

        return user
    }

    suspend fun getWabisFromUser(user: User) : LiveData<MutableList<User>>{
        val mutableList = MutableLiveData<MutableList<User>>()
        val DataList = mutableListOf<User>()
        println("probadno wabis")

        if (user.wabis != null) {
            for (user in user.wabis!!) {
                val wabi = getSingleUser(user)
                DataList.add(wabi)
            }
        }
        mutableList.value = DataList
        return mutableList
    }

    suspend fun getUsersFromList(users : ArrayList<String>?): MutableList<User> {

        val DataList = mutableListOf<User>()
        println("probadno wabis")
        if (users != null) {
            for (user in users) {
                val wabi = getSingleUser(user)
                DataList.add(wabi)
                println(wabi.name)
            }
        }
        return DataList
    }

    suspend fun getWabisList(idUser: String) : ArrayList<String>{
        var listWabis = arrayListOf<String>()
        val job1 = userList.document(idUser).get().addOnSuccessListener {
            listWabis = (it.data?.get("wabis") as ArrayList<String>)
        }

        job1.await()
        return listWabis
    }

    suspend fun addWabi(idUser: String, idWabi: String){
        var newArray = getWabisList(idUser)

        if (!newArray.contains(idWabi)) {
            newArray.add(idWabi)
        }
        userList.document(idUser).update("wabis", newArray)
    }

}