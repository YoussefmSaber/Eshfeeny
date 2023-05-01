package com.example.eshfeenygraduationproject.insurance_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentServicesInsuranceCardBinding


class ServicesInsuranceCardFragment : Fragment() {
    private var binding:FragmentServicesInsuranceCardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesInsuranceCardBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding?.root
    }
}