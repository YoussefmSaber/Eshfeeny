package com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet.tabFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.entity.pharmacyResponse.ProductX
import com.example.eshfeenygraduationproject.R

class PharmacyProductsFragment(val pharmacyProducts: List<ProductX>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_products, container, false)
    }
}