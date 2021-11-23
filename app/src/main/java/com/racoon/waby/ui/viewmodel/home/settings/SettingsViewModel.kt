package com.racoon.waby.ui.viewmodel.home.settings

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.racoon.waby.ui.view.auth.AuthActivity

class SettingsViewModel : ViewModel() {

    fun logOut(view: View) {
        val context: Context = view.context
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
    }
}