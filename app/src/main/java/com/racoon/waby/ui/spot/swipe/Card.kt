package com.racoon.waby.ui.spot.swipe

class Card(var userId: String? = null,
           var name: String? = null,
           var profileImageUrl: String? = null,
           var tags: ArrayList<String>? = null,
           var description: String? = null) {

    fun setUserID(userID: String?) {
        userId = userId
    }

}