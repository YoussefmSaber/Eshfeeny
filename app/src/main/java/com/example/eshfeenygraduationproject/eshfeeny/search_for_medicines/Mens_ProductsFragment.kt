package com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentMensProductsBinding
import com.example.eshfeenygraduationproject.eshfeeny.EshfeenyActivity
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragment


class Mens_ProductsFragment : Fragment() {
    private var binding:FragmentMensProductsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMensProductsBinding.inflate(layoutInflater)
        binding?.exit1BtnId?.setOnClickListener {
            (activity as EshfeenyActivity
                    ).replaceFragment(HomeFragment())

        }
        // Inflate the layout for this fragment
        return binding?.root
    }
    override fun onResume() {
        super.onResume()
        (activity as EshfeenyActivity).bottomNavigationView(true)
        (activity as EshfeenyActivity).View_search_in_fragments(true)
    }
}