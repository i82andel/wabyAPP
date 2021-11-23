package com.racoon.waby.ui.viewmodel.auth.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.common.SingleLiveEvent
import com.racoon.waby.ui.view.home.HomeActivity

class SplashViewModel() : ViewModel() {

    private val finishSLE = SingleLiveEvent<Boolean>()
    val finishLD: LiveData<Boolean> = finishSLE
    fun resume() {
        Looper.myLooper()?.also {
            Handler(it).postDelayed({
                finishSLE.value = (Firebase.auth.currentUser!=null)
            },2000)
        }
    }

    fun gotoHome(view: View) {
        val context: Context = view.context
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }
}