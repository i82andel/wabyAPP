package com.racoon.waby.data.repository

import androidx.annotation.IntegerRes
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.racoon.waby.common.Resource
import com.racoon.waby.common.SingleLiveEvent
import com.racoon.waby.data.model.User
import javax.inject.Singleton

interface UserRepository {

    fun signUpDefault(email: String, passwd: String, repeatedPasswd: String) : Resource<FirebaseUser?>?
    fun signInDefault(email: String, passwd: String) : Resource<FirebaseUser?>
    fun getCurrentUser() : User
    fun documentToUser(document: DocumentSnapshot) : User

}