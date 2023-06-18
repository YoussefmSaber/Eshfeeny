package com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            this.navigateToPharmacy.setOnClickListener {
                openGoogleMapsDirections()
            }
        }
        return binding?.root
    }

    private fun openGoogleMapsDirections() {
        val gmmIntentUri =
            Uri.parse("google.navigation:q=${pharmacy.geoLocation.lat},${pharmacy.geoLocation.lng}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}