package com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.entity.pharmacyResponse.PharmacyDetails
import com.example.domain.entity.pharmacyResponse.PharmacyResponseItem
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentPharmacyBottomSheetBinding
import com.example.eshfeenygraduationproject.eshfeeny.map.bottomSheet.adapter.PharmacyViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout

class PharmacyBottomSheetFragment(val pharmacy: PharmacyResponseItem) : BottomSheetDialogFragment() {

    private var binding: FragmentPharmacyBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPharmacyBottomSheetBinding.inflate(inflater)

        val pharmacyDetails = PharmacyDetails(pharmacy.address, pharmacy.phoneNumber)

        val adapter = PharmacyViewPagerAdapter(childFragmentManager, lifecycle, pharmacyDetails, pharmacy.products)

        binding?.apply {

            this.pharmacyName.text = "صيدلية ${pharmacy.name}"

            this.pharmacyViewPager.adapter = adapter
            this.pharmacyTabLayout.setOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        binding?.pharmacyViewPager?.currentItem = tab.position
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}