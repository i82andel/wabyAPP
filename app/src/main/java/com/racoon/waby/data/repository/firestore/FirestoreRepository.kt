package com.racoon.waby.data.repository.firestore

import com.racoon.waby.data.model.User

interface FirestoreRepository {

    fun createUser(user: User)
    fun modifyUser(user: User)

}