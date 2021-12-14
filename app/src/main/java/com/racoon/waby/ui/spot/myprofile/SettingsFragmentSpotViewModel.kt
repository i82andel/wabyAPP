package com.racoon.waby.ui.spot.myprofile

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.ui.auth.AuthActivity

class SettingsFragmentSpotViewModel : ViewModel() {

    fun logOut(view: View) {
        println(Firebase.auth.currentUser?.email)
        Firebase.auth.signOut()
        val context: Context = view.context
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
    }
}