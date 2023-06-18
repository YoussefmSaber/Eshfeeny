package com.example.eshfeenygraduationproject.eshfeeny.roshta

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentRoshtaBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class RoshtaFragment : Fragment() {
    private var binding: FragmentRoshtaBinding? = null
    private var selectedPhotoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentRoshtaBinding.inflate(layoutInflater)

        binding?.addRoshtaPhoto?.setOnClickListener {
            val bottomSheet =
                ImageBottomSheetFragment()
            bottomSheet.onPhotoSelected = { photoUri ->
                selectedPhotoUri = photoUri
                // Set the selected photo to your ImageView or perform any other necessary actions
                 binding?.addRoshtaPhoto?.setImageURI(photoUri)
            }
            bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")

        }

        binding?.exitBtnId?.setOnClickListener {
            findNavController().navigate(R.id.action_roshtaFragment_to_homeFragment2)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

    //for camera
    private lateinit var imageUri: Uri


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}