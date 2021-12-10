package com.racoon.waby.ui.spot.wabis

import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User

data class UserListState (
    val isLoading: Boolean  = false,
    val spots : List<User>? = emptyList(),
    val error: String = ""
)