package com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.repository.ProductRepoImpl
import com.example.data.utils.Constants
import com.example.eshfeenygraduationproject.databinding.FragmentImageBottomSheetBinding
import com.example.eshfeenygraduationproject.eshfeeny.brands.BrandItemsFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.brands.BrandsFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.roshta.RoshtaFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.searchForProducts.ProductCategoryFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.searchResults.SearchResultsFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream

class ImageBottomSheetFragment(private val imageFrom: String) : BottomSheetDialogFragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var productViewModel: ProductViewModel
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val REQUEST_IMAGE_CAPTURE = 100
    private val REQUEST_IMAGE_PICKER = 101
    private var binding: FragmentImageBottomSheetBinding? = null
    var onPhotoSelected: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageBottomSheetBinding.inflate(inflater)
        viewModelsInitialization()

        binding?.apply {
            this.camera.setOnClickListener {
                takeImage()
            }
            this.imagePicker.setOnClickListener {
                pickImage()
            }
        }

        return binding?.root
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICKER)
    }

    private fun takeImage() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, launch the camera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch the camera
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imgBitMap = data?.extras?.get("data") as Bitmap
            val imgFile = saveBitmapToFile(imgBitMap)
            productViewModel.uploadImage(Constants.IMAGE_UPLOAD_KEY, imgFile)
            productViewModel.imageResponseResult.observe(viewLifecycleOwner) {
                onPhotoSelected?.invoke(it.data.url)
                val searchResultAction = when (imageFrom) {
                    "home" -> HomeFragmentDirections.actionHomeFragment2ToSearchResultsFragment(
                            it.data.url
                        )

                    "category" -> ProductCategoryFragmentDirections.actionMedicineCategoryFragmentToSearchResultsFragment(
                            it.data.url
                        )

                    "brandItems" -> BrandItemsFragmentDirections.actionBrandItemsFragmentToSearchResultsFragment(
                            it.data.url
                        )

                    "brands" -> BrandsFragmentDirections.actionBrandsFragmentToSearchResultsFragment(
                            it.data.url
                        )

                    "search" -> SearchResultsFragmentDirections.actionSearchResultsFragmentSelf(it.data.url)

                    else -> SearchResultsFragmentDirections.actionSearchResultsFragmentSelf(it.data.url)
                }
                findNavController().navigate(searchResultAction)
            }
        } else if (requestCode == REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data

            if (imageUri != null) {
                val file = saveImageToFile(imageUri)
                productViewModel.uploadImage(Constants.IMAGE_UPLOAD_KEY, file)
                productViewModel.imageResponseResult.observe(viewLifecycleOwner) {
                    onPhotoSelected?.invoke(it.data.url)
                    Log.d("Image", it.data.url)
                    val searchResultAction = when (imageFrom) {
                        "home" -> HomeFragmentDirections.actionHomeFragment2ToSearchResultsFragment(
                            it.data.url
                        )

                        "category" -> ProductCategoryFragmentDirections.actionMedicineCategoryFragmentToSearchResultsFragment(
                            it.data.url
                        )

                        "brandItems" -> BrandItemsFragmentDirections.actionBrandItemsFragmentToSearchResultsFragment(
                            it.data.url
                        )

                        "brands" -> BrandsFragmentDirections.actionBrandsFragmentToSearchResultsFragment(
                            it.data.url
                        )

                        else -> SearchResultsFragmentDirections.actionSearchResultsFragmentSelf(it.data.url)
                    }
                    findNavController().navigate(searchResultAction)
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun saveImageToFile(image: Uri): File {
        val inputStream = image.let { requireContext().contentResolver.openInputStream(it) }
        val file = File(context?.cacheDir, "selected_image.png")
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    private fun saveBitmapToFile(bitmap: Bitmap): File {
        val file = File(requireContext().cacheDir, "image.png")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return file
    }

    private fun viewModelsInitialization() {
        val medicineRepo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(medicineRepo)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}