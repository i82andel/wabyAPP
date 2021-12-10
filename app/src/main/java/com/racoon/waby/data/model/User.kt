package com.racoon.waby.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.Date


class User (val idUser: String? = null,
                 val name: String? = null,
                 val surname: String? = null,
                 val userName: String? = null,
                 val birthdate: Date? = null,
                 val email:String? = null,
                 val gender: String? = null,
                 val phoneNumber: String? = null,
                 val description: String? = null,
                 val tags: ArrayList<String>? = null,
                 val images: String = "gs://racoonapps-cd246.appspot.com/profiles/placeholder.png",
                 val wabis: ArrayList<String>? = null
){


    fun getAge() : String{

        return "19"

    }
}

enum class Gender {
    MEN,
    WOMEN,
    NONBINARY
}