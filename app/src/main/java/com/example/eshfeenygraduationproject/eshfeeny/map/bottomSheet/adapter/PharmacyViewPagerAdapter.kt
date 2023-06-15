package com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet.tabFragments.PharmacyDetailsFragment
import com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet.tabFragments.PharmacyProductsFragment

class PharmacyViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PharmacyDetailsFragment()
            else -> PharmacyProductsFragment()
        }
    }
}