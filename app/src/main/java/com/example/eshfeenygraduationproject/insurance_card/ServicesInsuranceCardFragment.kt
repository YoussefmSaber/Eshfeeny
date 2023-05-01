package com.example.eshfeenygraduationproject.insurance_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentServicesInsuranceCardBinding


class ServicesInsuranceCardFragment : Fragment() {
    private var binding:FragmentServicesInsuranceCardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesInsuranceCardBinding.inflate(layoutInflater)
        var num = arguments?.getInt("num")
        when (num) {
            1 -> binding?.TVId?.text = "EGYCARE"
            2 -> binding?.TVId?.text = "MetLife"
            3 -> binding?.TVId?.text = "Misr"
            else -> binding?.TVId?.text = "${num.toString()}"
        }



        binding?.backBtn22?.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        // Inflate the layout for this fragment
        return binding?.root
    }
}