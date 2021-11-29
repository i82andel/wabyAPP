package com.racoon.waby.ui.auth.registerdata.description

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.racoon.waby.ui.home.HomeActivity

class RegisterUserDescriptionViewModel : ViewModel() {

    fun gotoHome(view: View) {
        val context: Context = view.context
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }
}