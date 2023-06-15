package com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.entity.pharmacyResponse.PharmacyDetails
import com.example.domain.entity.pharmacyResponse.PharmacyResponseItem
import com.example.eshfeenygraduationproject.databinding.FragmentPharmacyBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PharmacyBottomSheetFragment(val pharmacy: PharmacyResponseItem) :
    BottomSheetDialogFragment() {

    private var binding: FragmentPharmacyBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPharmacyBottomSheetBinding.inflate(inflater)

        binding?.apply {

            this.pharmacyName.text = "صيدلية ${pharmacy.name}"
            if (pharmacy.address == null) {
                this.pharmacyAddressTextView.text = "لا يوجد عنوان"
            } else {
                this.pharmacyAddressTextView.text = pharmacy.address
            }
            this.pharmacyPhoneTextView.text = pharmacy.phoneNumber
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}