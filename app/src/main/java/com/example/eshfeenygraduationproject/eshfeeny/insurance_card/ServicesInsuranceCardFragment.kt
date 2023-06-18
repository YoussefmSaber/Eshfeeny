package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.databinding.FragmentServicesInsuranceCardBinding


class ServicesInsuranceCardFragment : Fragment() {
    private var binding: FragmentServicesInsuranceCardBinding? = null
    private val args: ServicesInsuranceCardFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesInsuranceCardBinding.inflate(layoutInflater)

        when (args.insuranceCardName) {
            "EgyCare" -> binding?.TVId?.text = "EGYCARE"
            "MetLife" -> binding?.TVId?.text = "MetLife"
            "Misr" -> binding?.TVId?.text = "Misr"
            else -> Log.i("insuranceCard", "Error unknown entry")
        }

        binding?.monthlyMedicationId?.setOnClickListener {
            val action =
                ServicesInsuranceCardFragmentDirections
                    .actionServicesInsuranceCardFragmentToCartExistsFragment(
                    args.insuranceCardName
                )
            findNavController().navigate(action)
        }
        binding?.addRoshtaId?.setOnClickListener {
            val action =
                ServicesInsuranceCardFragmentDirections
                    .actionServicesInsuranceCardFragmentToAddRoshtaICFragment(
                    args.insuranceCardName
                )
            findNavController().navigate(action)
        }

        binding?.backBtn22?.setOnClickListener {
            val action =
                ServicesInsuranceCardFragmentDirections
                    .actionServicesInsuranceCardFragmentToInsuranceCardFragment()
            findNavController().navigate(action)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }
}