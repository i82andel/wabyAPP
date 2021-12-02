package com.racoon.waby.ui.spot

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.racoon.waby.R
import com.racoon.waby.data.model.Spot
import com.racoon.waby.databinding.ActivitySpotBinding

class SpotActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpotBinding
    private val spot : Spot? = null
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //val navController = findNavController(R.id.fragmentContainerView2)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_spot) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_notifications,R.id.navigation_swipe,R.id.navigation_home, R.id.navigation_wabis,R.id.navigation_extras)
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }



}