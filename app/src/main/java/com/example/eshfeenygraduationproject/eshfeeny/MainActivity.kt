package com.example.eshfeenygraduationproject.eshfeeny

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_EshfeenyGraduationProject)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.toHomeFragment -> {
                    navController.navigate(R.id.toHomeFragment)
                    true
                }

                R.id.toMoreFragment -> {
                    navController.navigate(R.id.toMoreFragment)
                    true
                }

                R.id.toCartFragment -> {
                    navController.navigate(R.id.toCartFragment)
                    true
                }

                R.id.mapsFragment -> {
                    navController.navigate(R.id.mapsFragment)
                    true
                }

                R.id.toFavoriteFragment -> {
                    navController.navigate(R.id.toFavoriteFragment)
                    true
                }

                else -> false
            }
        }
    }
}