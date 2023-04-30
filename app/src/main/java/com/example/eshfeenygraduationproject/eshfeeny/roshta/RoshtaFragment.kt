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
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class RoshtaFragment : Fragment() {
    private var binding:FragmentRoshtaBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentRoshtaBinding.inflate(layoutInflater)

        binding?.addRoshtaPhoto?.setOnClickListener {
            val view: View = inflater.inflate(R.layout.bottomsheetlayout, null)
            val dialog = BottomSheetDialog(requireContext())
            val gallery: ImageView = view.findViewById(R.id.galleryId)
            var camera: ImageView = view.findViewById(R.id.photoId)
            gallery.setOnClickListener {
                pickPhotoGallery()
                runBlocking {
                    delay(3000)
                }
                binding?.txtChoosePhoto?.visibility = View.VISIBLE
                binding?.addRoshtaPhoto?.visibility = View.INVISIBLE
                binding?.btnNext?.text = "أعرف أقرب صيدلية"
                binding?.clearBtnId?.visibility = View.VISIBLE
                binding?.takephoto?.visibility = View.VISIBLE
                binding?.clearBtnId?.setOnClickListener {
                    binding?.txtChoosePhoto?.visibility = View.GONE
                    binding?.takephoto?.visibility = View.GONE
                    binding?.addRoshtaPhoto?.visibility = View.VISIBLE
                    binding?.clearBtnId?.visibility = View.GONE
                }
            }
            camera.setOnClickListener {
                openCamera()
                runBlocking {
                    delay(3000)
                }
                binding?.txtChoosePhoto?.visibility = View.VISIBLE
                binding?.addRoshtaPhoto?.visibility = View.INVISIBLE
                binding?.btnNext?.text = "أعرف أقرب صيدلية"
                binding?.clearBtnId?.visibility = View.VISIBLE
                binding?.takephoto?.visibility = View.VISIBLE
                binding?.clearBtnId?.setOnClickListener {
                    binding?.txtChoosePhoto?.visibility = View.GONE
                    binding?.takephoto?.visibility = View.GONE
                    binding?.addRoshtaPhoto?.visibility = View.VISIBLE
                    binding?.clearBtnId?.visibility = View.GONE
                }
            }
            dialog.setContentView(view)
            dialog.show()
        }

        binding?.exitBtnId?.setOnClickListener {
            findNavController().navigate(R.id.action_roshtaFragment_to_homeFragment2)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }
    //for gallery
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

        startActivityForResult(cameraIntent, 0)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val imageUri = data?.data
            binding?.takephoto?.setImageURI(data?.data)
        } else if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            binding?.takephoto?.setImageURI(imageUri)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}