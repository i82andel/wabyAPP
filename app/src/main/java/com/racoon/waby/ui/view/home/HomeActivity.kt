package com.racoon.waby.ui.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.databinding.ActivityHomeBinding
import com.racoon.waby.databinding.FragmentRegisterUserGenderBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.homeFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        //llama al metodo para volver al fragmet anterior
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    //para volver al fragment anterior
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}