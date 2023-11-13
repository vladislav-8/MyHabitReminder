package com.practicum.myhabitreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.practicum.myhabitreminder.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        //если понадобится больше окон

        /*mainBinding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.createHabitFragment, R.id.appFragment, R.id.settingsFragment -> mainBinding.bottomNavigationView.isVisible = true
                else -> mainBinding.bottomNavigationView.isVisible = false
            }
        }*/

        viewModel.getAuthState().observe(this) { isSignedIn ->
            if (isSignedIn)
                navController.navigate(R.id.appFragment)
            else
                navController.navigate(R.id.loginFragment)
        }
    }
}