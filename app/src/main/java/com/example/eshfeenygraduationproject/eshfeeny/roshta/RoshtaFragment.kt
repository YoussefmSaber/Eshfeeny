package com.example.eshfeenygraduationproject.eshfeeny.roshta

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentRoshtaBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment


class RoshtaFragment : Fragment() {
    private var binding: FragmentRoshtaBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentRoshtaBinding.inflate(layoutInflater)

        binding?.addRoshtaPhoto?.setOnClickListener {
            val bottomSheet =
                ImageBottomSheetFragment()
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