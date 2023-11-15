package com.minegksn.capstone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.minegksn.capstone.MainApplication
import com.minegksn.capstone.R
import com.minegksn.capstone.common.gone
import com.minegksn.capstone.common.viewBinding
import com.minegksn.capstone.common.visible
import com.minegksn.capstone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView2, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.favoritesFragment,
                R.id.bagFragment,
                R.id.searchFragment,
                -> {
                    binding.bottomNavigationView2.visible()
                }

                else -> {
                    binding.bottomNavigationView2.gone()
                }
            }
        }

    }
}