package com.racoon.waby.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.racoon.waby.R
import com.racoon.waby.databinding.ActivityHomeBinding

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