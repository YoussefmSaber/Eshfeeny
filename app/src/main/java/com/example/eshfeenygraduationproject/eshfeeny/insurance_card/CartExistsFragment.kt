package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentCartExistsBinding


class CartExistsFragment : Fragment() {
    private var binding:FragmentCartExistsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartExistsBinding.inflate(layoutInflater)
        var num = arguments?.getInt("num2")
        when (num) {
            1 -> binding?.tV2?.text = "EGYCARE"
            2 -> binding?.tV2?.text = "MetLife"
            3 -> binding?.tV2?.text = "Misr"
            else -> binding?.tV2?.text = "${num.toString()}"
        }

        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_cartExistsFragment_to_servicesInsuranceCardFragment)
        }
        binding?.addCartExistsBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_cartExistsFragment_to_infoInsuranceCardFragment)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

}