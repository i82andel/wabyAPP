package com.racoon.waby.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.racoon.waby.R
import com.racoon.waby.databinding.ActivityMainBinding

class AuthActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        //llama al metodo para volver al fragmet anterior
        NavigationUI.setupActionBarWithNavController(this,navController)



    }
    //para volver al fragment anterior
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }






}

