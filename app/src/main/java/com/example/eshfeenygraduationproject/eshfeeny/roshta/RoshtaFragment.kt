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
            bottomSheet.onPhotoSelected = { imageUrl ->
                // Set the selected photo to your ImageView or perform any other necessary actions
                binding?.addRoshtaPhoto?.loadUrl(imageUrl)
                binding?.clearBtnId?.visibility = View.VISIBLE
                imgUrl = imageUrl
                binding?.btnNext?.isEnabled = true
            }
            bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
        }

        binding?.clearBtnId?.setOnClickListener {
            binding?.addRoshtaPhoto?.setImageResource(R.drawable.add3)
            imgUrl = ""
            binding?.btnNext?.isEnabled = false
            it.visibility = View.GONE
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