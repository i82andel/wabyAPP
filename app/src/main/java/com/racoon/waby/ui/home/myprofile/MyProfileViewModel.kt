package com.racoon.waby.ui.home.myprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.data.model.Gender
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.UserRepository
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCase
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import java.util.*

class MyProfileViewModel(private val authUserUseCase: AuthUserUseCase) : ViewModel()  {

    init {
        getCurrentUser()
    }

    fun getCurrentUser() : User {

        val uid = "7dscT5MXidZQjtL51MPQRi5ZdK62"
            val db = Firebase.firestore
            val userList = db.collection("User")
            var user = User()

        val RefDoc = userList.document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                user = documentToUser(document)

            }
        }

        return user
       //return authUserUseCase.firebaseDefaultGetCurrentUser()
    }

    fun documentToUser(document: DocumentSnapshot) : User {

        val email = document.getString("email")
        val name = document.getString("name")
        val surname = document.getString("surname")
        val userName = document.getString("username")
        val birthdate = Date(2,1,2000)
        val phoneNumber = document.getString("phoneNumber")
        val description = document.getString("description")


        val user = User(
            "",
            name,
            surname,
            userName,
            birthdate,
            email,
            "",
            Gender.MEN,
            phoneNumber,
            description)

        return user
    }
}