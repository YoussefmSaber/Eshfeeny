package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentInsuranceCardBinding


class InsuranceCardFragment : Fragment() {
    private var binding: FragmentInsuranceCardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsuranceCardBinding.inflate(layoutInflater)

        binding?.EgyCareBtnId?.setOnClickListener {
            onInsuranceCardButtonClick("EgyCare")
        }
        binding?.MetLifeBtnId?.setOnClickListener {

            onInsuranceCardButtonClick("MetLife")
        }
        binding?.MisrBtnId?.setOnClickListener {
            onInsuranceCardButtonClick("Misr")
        }

        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_insuranceCardFragment_to_homeFragment2)
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onInsuranceCardButtonClick(insuranceCardName: String) {
        val action =
            InsuranceCardFragmentDirections.actionInsuranceCardFragmentToServicesInsuranceCardFragment(
                insuranceCardName
            )
        findNavController().navigate(action)
    }
}