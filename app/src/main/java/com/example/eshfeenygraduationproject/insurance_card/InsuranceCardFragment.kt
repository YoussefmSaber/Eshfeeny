package com.example.eshfeenygraduationproject.insurance_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentInsuranceCardBinding


class InsuranceCardFragment : Fragment() {
    private var binding:FragmentInsuranceCardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsuranceCardBinding.inflate(layoutInflater)



        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_insuranceCardFragment_to_homeFragment2)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }
}