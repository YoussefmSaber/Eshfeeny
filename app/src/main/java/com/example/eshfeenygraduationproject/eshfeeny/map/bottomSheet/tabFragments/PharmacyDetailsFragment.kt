package com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet.tabFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.entity.pharmacyResponse.PharmacyDetails
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentPharmacyDetailsBinding

class PharmacyDetailsFragment(val pharmacyDetails: PharmacyDetails) : Fragment() {

    private var binding: FragmentPharmacyDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPharmacyDetailsBinding.inflate(inflater)

        binding?.apply {
            this.pharmacyAddressTextView.text = pharmacyDetails.address
            this.pharmacyPhoneTextView.text = pharmacyDetails.phoneNumber
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}