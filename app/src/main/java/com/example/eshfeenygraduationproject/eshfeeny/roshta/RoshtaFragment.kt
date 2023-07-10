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
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl


class RoshtaFragment : Fragment() {
    private var binding: FragmentRoshtaBinding? = null
    private var selectedPhotoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentRoshtaBinding.inflate(layoutInflater)
        binding?.btnNext?.isEnabled = false
        var imgUrl = ""
        binding?.addRoshtaPhoto?.setOnClickListener {
            val bottomSheet =
                ImageBottomSheetFragment("roshta")
            bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
            bottomSheet.onPhotoSelected = { imageUrl ->
                // Set the selected photo to your ImageView or perform any other necessary actions
                binding?.roshitaImageView?.loadUrl(imageUrl)
                bottomSheet.dismiss()
                binding?.roshitaImageView?.visibility = View.VISIBLE
                binding?.clearBtnId?.visibility = View.VISIBLE
                binding?.AddRoshetaLayout?.visibility = View.GONE
                imgUrl = imageUrl
                binding?.btnNext?.isEnabled = true
            }
        }

        binding?.clearBtnId?.setOnClickListener {
            imgUrl = ""
            binding?.btnNext?.isEnabled = false
            binding?.roshitaImageView?.visibility = View.GONE
            binding?.clearBtnId?.visibility = View.GONE
            binding?.AddRoshetaLayout?.visibility = View.VISIBLE
        }

        binding?.btnNext?.setOnClickListener {
            val action =
                RoshtaFragmentDirections.actionRoshtaFragmentToSearchResultsFragment(imgUrl)
            findNavController().navigate(action)
        }

        binding?.exitBtnId?.setOnClickListener {
            findNavController().navigate(R.id.action_roshtaFragment_to_homeFragment2)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}