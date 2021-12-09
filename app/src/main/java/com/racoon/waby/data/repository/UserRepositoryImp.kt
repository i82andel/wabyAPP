package com.racoon.waby.data.repository

import android.util.Log
import androidx.annotation.IntegerRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.common.Resource
import com.racoon.waby.common.SingleLiveEvent
import com.racoon.waby.data.model.Gender
import com.racoon.waby.data.model.Image
import com.racoon.waby.data.model.User
import java.util.*
import kotlin.collections.ArrayList


class UserRepositoryImp : UserRepository {

    override fun signUpDefault(email: String, passwd: String, repeatedPasswd: String) : Resource<FirebaseUser?>? {
        println("creando")

       /* Firebase.auth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener {authResult->
            if (taskCompleted) return@addOnCompleteListener
            taskCompleted = true

            val newUser = Firebase.auth.currentUser
            if (authResult.isSuccessful && newUser != null) {
                /*newUser.sendEmailVerification().addOnCompleteListener { emailTask->

                }
                Firebase.auth.signOut()*/
                successSignUp.postValue(authResult.isSuccessful)
            }else {
                println("No se ha podido registrar")
                error.value = R.string.signup_error


            }
        }*/

        Firebase.auth.createUserWithEmailAndPassword(email,passwd)
        val user = Firebase.auth.currentUser
        return if (user != null) {
            println(user.email)
            Resource.Result(user)
        } else {
            null
        }

    }

    override fun signInDefault(email: String, passwd: String) : Resource<FirebaseUser?>{

        println("inicio sesion")

        Firebase.auth.signInWithEmailAndPassword(email,passwd)
        val user = Firebase.auth.currentUser

        if (user != null) {
            println(user.email)
        }
        return Resource.Result(user)
    }

    override fun getCurrentUser() : User{

        val uid = "7dscT5MXidZQjtL51MPQRi5ZdK62"
        val db = Firebase.firestore
        val userList = db.collection("User")

        val RefDoc = userList.document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                //user = documentToUser(document)

            }
        }

        val image = Image("1", "@drawable/img")
        val images = ArrayList<Image>()
        images.add(image)

        val user = User(
            "",
            "Paco",
            "Martinez",
            "pacoo23",
            Date(1,1, 2000),
            "paquito@gmail.com",
            "",
            null,
            "600123456",
            null,
            null,
            null)


        return user

    }

    override fun documentToUser(document: DocumentSnapshot) : User {

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
            null,
            phoneNumber,
            null)

        return user
    }


}

