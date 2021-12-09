package com.racoon.waby.data.model

import java.util.Date


data class User (val idUser: String? = null,
                 val name: String? = null,
                 val surname: String? = null,
                 val userName: String? = null,
                 val birthdate: Date? = null,
                 val email:String? = null,
                 val gender: String? = null,
                 val phoneNumber: String? = null,
                 val description: String? = null,
                 val tags: ArrayList<String>? = null,
                 val images: ArrayList<String>? = null,
                 val wabis: ArrayList<String>? = null
)

enum class Gender {
    MEN,
    WOMEN,
    NONBINARY
}