package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAddPhotoICBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.InsuranceCardAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class AddPhotoICFragment : Fragment() {
    private var binding:FragmentAddPhotoICBinding?= null
    private lateinit var userViewModel: UserViewModel
    private val args:AddPhotoICFragmentArgs by navArgs()
    private lateinit var userId: String
    private lateinit var adapter: InsuranceCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotoICBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]





        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_addPhotoICFragment_to_infoInsuranceCardFragment)
        }



        //For open camera or gallery
        val view: View = inflater.inflate(R.layout.bottomsheetlayout, null)
        val dialog = BottomSheetDialog(requireContext())
        val gallery: ImageView = view.findViewById(R.id.galleryId)
        var camera: ImageView = view.findViewById(R.id.photoId)
        var flag = 0
        binding?.addphotoICId?.setOnClickListener {

            gallery.setOnClickListener {
                pickPhotoGallery()
                Thread.sleep(3000)
                binding?.addphotoICId?.visibility = View.INVISIBLE
                binding?.ReUploadCardId?.visibility = View.VISIBLE
                binding?.uploadPhotoId?.visibility = View.VISIBLE
                binding?.addCartExistsBtn?.setTextColor(Color.parseColor("#F5F5F5"))
                binding?.addCartExistsBtn?.setBackgroundColor(Color.parseColor("#0583F2"))
            }
            camera.setOnClickListener {
                openCamera()
                Thread.sleep(3000)
                binding?.addphotoICId?.visibility = View.INVISIBLE
                binding?.ReUploadCardId?.visibility = View.VISIBLE
                binding?.uploadPhotoId?.visibility = View.VISIBLE
                binding?.addCartExistsBtn?.setTextColor(Color.parseColor("#F5F5F5"))
                binding?.addCartExistsBtn?.setBackgroundColor(Color.parseColor("#0583F2"))
            }
            dialog.setContentView(view)
            dialog.show()
            flag = 1

        }
        binding?.ReUploadCardId?.setOnClickListener {
            gallery.setOnClickListener {
                pickPhotoGallery()
            }
            camera.setOnClickListener {
                openCamera()
            }
            dialog.setContentView(view)
            dialog.show()
            flag = 1
        }
        //For User View Model
        userViewModel.userData.observe(viewLifecycleOwner){ userData ->
            userId = userData._id
            val userCard =
                InsuranceCardX("","2598","Madaa","")
            userViewModel.addInsuranceCard(userId,userCard)
        }
        binding?.addCartExistsBtn?.setOnClickListener {
            if(flag == 0){
                Toast.makeText(context,"قم برفع الصورة", Toast.LENGTH_SHORT).show()
            }
            else {
                findNavController()
                    .navigate(R.id.action_addPhotoICFragment_to_cartExistsFragment)
            }

        }

        return binding?.root
    }
    private fun pickPhotoGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)

    }
    //for camera
    private lateinit var imageUri: Uri
    private fun openCamera() {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "New Picture")
            put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        }

        imageUri = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }

        startActivityForResult(cameraIntent, 100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val imageUri = data?.data
            binding?.uploadPhotoId?.setImageURI(imageUri)
        } else if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            binding?.uploadPhotoId?.setImageURI(imageUri)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}