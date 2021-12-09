package com.racoon.waby.data.model

import com.google.firebase.firestore.local.ReferenceSet
import java.util.*
import kotlin.collections.ArrayList

data class Event(val idEvent :  String,
                 val assistants : ArrayList<String>,
                 val attendances :  ArrayList<String>,
                 val eventLog : ReferenceSet,
                 val eventDate : Date,
                 val spot: String)
