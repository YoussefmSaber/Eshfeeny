package com.example.eshfeenygraduationproject.eshfeeny.bmiandbmr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.databinding.FragmentVerifyDataBinding


class VerifyDataFragment : Fragment() {
    private var binding:FragmentVerifyDataBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyDataBinding.inflate(layoutInflater)
        val age = arguments?.getInt("Age")
        val height = arguments?.getInt("Height")
        val weight = arguments?.getInt("Weight")
        val gender = arguments?.getString("Gender")
        binding?.txtAgeId?.text = "$age"
        binding?.txtCmId?.text = "$height"
        binding?.txtKgId?.text = "$weight"

        // Inflate the layout for this fragment
        return binding?.root
    }

}