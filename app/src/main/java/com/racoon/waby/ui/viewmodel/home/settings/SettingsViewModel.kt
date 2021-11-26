package com.racoon.waby.ui.viewmodel.home.settings

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.ui.view.auth.AuthActivity

class SettingsViewModel : ViewModel() {

    fun logOut(view: View) {
        println(Firebase.auth.currentUser?.email)
        Firebase.auth.signOut()
        val context: Context = view.context
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
    }
}