package com.hackatron52.androidacademyhackathon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hackatron52.androidacademyhackathon.presentation.activity.NavBarShowingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavBarShowingActivity {

    private var navigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navigationView?.setupWithNavController(navController)
    }

    override fun showBottomNavigationView() {
        navigationView?.isVisible = true
    }

    override fun hideBottomNavigationView() {
        navigationView?.isVisible = false
    }
}