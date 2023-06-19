package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAddPhotoICBinding
import com.example.eshfeenygraduationproject.databinding.FragmentAddRoshtaICBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment

class AddRoshtaICFragment : Fragment() {
    private var binding: FragmentAddRoshtaICBinding? = null
    private var selectedPhotoUri: Uri? = null
    private val args:AddRoshtaICFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoshtaICBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        when (args.insuranceCardName) {
            "EgyCare" -> binding?.tV2?.text = "EGYCARE"
            "MetLife" -> binding?.tV2?.text = "MetLife"
            "Misr" -> binding?.tV2?.text = "Misr"
            else -> Log.i("insuranceCard", "Error unknown entry")
        }
        binding?.addRoshtaPhoto?.setOnClickListener {
            val bottomSheet =
                ImageBottomSheetFragment("addRoshta")
            bottomSheet.onPhotoSelected = { photoUri ->
                selectedPhotoUri = photoUri
                // Set the selected photo to your ImageView or perform any other necessary actions
                binding?.addRoshtaPhoto?.setImageURI(photoUri)
                binding?.saveButton?.setTextColor(Color.parseColor("#F5F5F5"))
                binding?.saveButton?.setBackgroundColor(Color.parseColor("#0583F2"))
            }
            bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")

        }
        binding?.backBtn22?.setOnClickListener {
            findNavController()
                .navigate(R.id.action_addRoshtaICFragment_to_servicesInsuranceCardFragment)
        }
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}