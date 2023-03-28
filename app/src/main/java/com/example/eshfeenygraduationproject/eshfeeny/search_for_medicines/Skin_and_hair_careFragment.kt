package com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSkinAndHairCareBinding


class Skin_and_hair_careFragment : Fragment() {
    private var binding:FragmentSkinAndHairCareBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSkinAndHairCareBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding?.root
    }
}