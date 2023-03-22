package com.example.eshfeenygraduationproject.eshfeeny

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
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
                R.id.homeFragment -> {
                    replaceFragment(HomeFragment())
                    binding?.view1?.visibility = View.VISIBLE
                    binding?.imageView5?.visibility = View.VISIBLE
                    binding?.SearchId?.visibility = View.VISIBLE
                    binding?.computerVesionImageViewId?.visibility = View.VISIBLE
                }
                R.id.cartFragment -> {
                    replaceFragment(CartFragment())
                    View_search_in_fragments()
                }
                R.id.gpsFragment -> {replaceFragment(GpsFragment())
                    View_search_in_fragments()
                }
                R.id.favoriteFragment -> {replaceFragment(FavoriteFragment())
                    View_search_in_fragments()}
                R.id.moreFragment -> {replaceFragment(MoreFragment())
                    View_search_in_fragments()}
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
    private fun View_search_in_fragments(){
        binding?.view1?.visibility = View.GONE
        binding?.imageView5?.visibility = View.GONE
        binding?.SearchId?.visibility = View.GONE
        binding?.computerVesionImageViewId?.visibility = View.GONE
    }

}
