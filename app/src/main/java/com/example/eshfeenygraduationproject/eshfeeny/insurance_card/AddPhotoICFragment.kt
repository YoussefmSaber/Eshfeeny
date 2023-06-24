package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAddPhotoICBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl


class AddPhotoICFragment : Fragment() {
    private var binding: FragmentAddPhotoICBinding? = null
    private lateinit var userViewModel: UserViewModel
    private val args: AddPhotoICFragmentArgs by navArgs()
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotoICBinding.inflate(inflater, container, false)
        Log.d("Insurance Card", args.toString())
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        var imageUrl = ""
        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_addPhotoICFragment_to_infoInsuranceCardFragment)
        }

        binding?.addPhotoICid?.setOnClickListener {
            val bottomSheet = ImageBottomSheetFragment("insuranceCard")
            bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
            bottomSheet.onPhotoSelected = { imgUrl ->
                binding?.addPhotoICid?.loadUrl(imgUrl)
                binding?.ReUploadCardId?.visibility = View.VISIBLE
                imageUrl = imgUrl
            }
        }

        binding?.ReUploadCardId?.setOnClickListener {
            val bottomSheet = ImageBottomSheetFragment("insuranceCard")
            bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
            bottomSheet.onPhotoSelected = { imgUrl ->
                binding?.addPhotoICid?.loadUrl(imgUrl)
                binding?.ReUploadCardId?.visibility = View.VISIBLE
                imageUrl = imgUrl
            }
        }

        binding?.addCartExistsBtn?.setOnClickListener {
            userViewModel.userData.observe(viewLifecycleOwner) { userData ->
                userId = userData._id.toString()
                val userCard =
                    InsuranceCardX(
                        name = args.InsuranceCardName,
                        number = args.cardNumber,
                        nameOnCard = args.cardName,
                        imageURL = imageUrl
                    )
                Log.d("Insurance Card", userCard.toString())
                userViewModel.addInsuranceCard(userId, userCard)
                val action =
                    AddPhotoICFragmentDirections.actionAddPhotoICFragmentToCartExistsFragment(
                        args.InsuranceCardName
                    )
                findNavController().navigate(action)
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}