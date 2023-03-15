package com.example.eshfeenygraduationproject.eshfeeny

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.ActivityEshfeenyBinding
import com.example.eshfeenygraduationproject.eshfeeny.cart.CartFragment
import com.example.eshfeenygraduationproject.eshfeeny.favorite.FavoriteFragment
import com.example.eshfeenygraduationproject.eshfeeny.gps.GpsFragment
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragment
import com.example.eshfeenygraduationproject.eshfeeny.more.MoreFragment

class EshfeenyActivity: AppCompatActivity() {
    private var binding:ActivityEshfeenyBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEshfeenyBinding.inflate(layoutInflater)
        // To change the splash screen theme to the default theme for the application
        setTheme(R.style.Theme_EshfeenyGraduationProject)
        setContentView(binding?.root)
        replaceFragment(HomeFragment())
        binding?.bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.cartFragment -> replaceFragment(CartFragment())
                R.id.gpsFragment -> replaceFragment(GpsFragment())
                R.id.favoriteFragment -> replaceFragment(FavoriteFragment())
                R.id.moreFragment -> replaceFragment(MoreFragment())
                else ->{

                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment,fragment)
        fragmentTransaction.commit()
    }
}
