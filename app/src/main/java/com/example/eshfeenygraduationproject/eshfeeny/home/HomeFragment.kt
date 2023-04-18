package com.example.eshfeenygraduationproject.eshfeeny.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapterHome
import com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines.*
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.UserViewModel


class HomeFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var medicineViewModel: MedicineViewModel
    private var binding: FragmentHomeBinding? = null

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val medicineRepo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(medicineRepo)


        val imgList = ArrayList<SlideModel>()
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318089572432/flip_img1.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318370586674/flip_img2.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318664192031/flip_img3.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498318932623361/flip_img4.png"))
        imgList.add(SlideModel("https://cdn.discordapp.com/attachments/981587143094845490/1095498319305912380/flip_img5.png"))

        binding?.imageSLider?.setImageList(imgList, ScaleTypes.FIT)
        binding?.imageSLider?.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)

        //To open Roshta fragment

        //To open Search Medicine Fragment
        binding?.searchForMedsBtn?.setOnClickListener {
            navigateToRightCategory("allMeds", it)
        }

        //To open Virus Protection Fragment
        binding?.virusProtectionBtn?.setOnClickListener {
            navigateToRightCategory("virusProtection", it)
        }

        //To open Mother and Child Fragment
        binding?.motherAndChildBtn?.setOnClickListener {
            navigateToRightCategory("motherAndChild", it)
        }

        //To open Women's products Fragment
        binding?.womenProductsBtn?.setOnClickListener {
            navigateToRightCategory("womenProducts", it)
        }

        //To open Skin and hair care Fragment
        binding?.skinAndHairCareBtn?.setOnClickListener {
            navigateToRightCategory("skinAndHairCare", it)
        }

        //To open Dental care Fragment
        binding?.dentalCareBtn?.setOnClickListener {
            navigateToRightCategory("dentalCareBtn", it)
        }

        //To open Men's products Fragment
        binding?.menProductsBtn?.setOnClickListener {
            navigateToRightCategory("menProducts", it)
        }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        medicineViewModel = ViewModelProvider(this, viewModelFactory)[MedicineViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner){ userData ->
            val userID = userData._id

            medicineViewModel.getMedicineForEmsaak()
            medicineViewModel.categories_Emsaak.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapterHome(medicineViewModel, userID)
                binding?.medicineIdRv?.adapter = adapter
                adapter.submitList(it)
            }
            //call recycler view for كحه
            medicineViewModel.getMedicineForKo7aa()
            medicineViewModel.categories_Ko7aa.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapterHome(medicineViewModel, userID)
                binding?.medicineIdRv2?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Frgament sh8aal for ko7aa", it.toString())
            }
            //call recycler view for مغص
            medicineViewModel.getMedicineForM8aas()
            medicineViewModel.categories_M8aas.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapterHome(medicineViewModel, userID)
                binding?.medicineIdRv3?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Frgament sh8aal", it.toString())
            }
        }

        //call recycler view for امساك
        return binding?.root
    }

    private fun navigateToRightCategory(categoryName: String, view: View) {
        val action = HomeFragmentDirections.actionHomeFragment2ToMedicineCategoryFragment(categoryName)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.medicineIdRv?.adapter = null
        binding?.medicineIdRv2?.adapter = null
        binding?.medicineIdRv3?.adapter = null
    }
}