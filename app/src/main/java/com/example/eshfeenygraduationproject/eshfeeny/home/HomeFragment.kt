package com.example.eshfeenygraduationproject.eshfeeny.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.EshfeenyActivity
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapter
import com.example.eshfeenygraduationproject.eshfeeny.roshta.RoshtaFragment
import com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines.*
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var medicineViewModel: MedicineViewModel

    private var binding: FragmentHomeBinding? = null

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)
        //To open Roshta fragment
        binding?.addRoshtaPhotoId?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(RoshtaFragment())
        }
        //To open Search Medicine Fragment
        binding?.SearchForMedBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Search_for_medicinesFragment())
        }
        ////To open Virus Protection Fragment
        binding?.VPBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Virus_ProtectionFragment())
        }
        //To open Mother and Child Fragment
        binding?.MACBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Mother_and_ChildFragment())
        }
        //To open Women's products Fragment
        binding?.WPBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Womens_ProductsFragment())
        }
        //To open Skin and hair care Fragment
        binding?.SAHBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Skin_and_hair_careFragment())
        }
        //To open Dental care Fragment
        binding?.DCBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Dental_CareFragment())
        }
        //To open Men's products Fragment
        binding?.MPBtnIdHome?.setOnClickListener {
            (
                    activity as EshfeenyActivity
                    ).replaceFragment(Mens_ProductsFragment())
        }



        medicineViewModel = ViewModelProvider(this, viewModelFactory)[MedicineViewModel::class.java]

        //call recycler view for امساك
        medicineViewModel.getMedicineForEmsaak()
        medicineViewModel.categories_Emsaak.observe(viewLifecycleOwner) {
            val adapter = MedicineAdapter()
            binding?.medicineIdRv?.adapter = adapter
            adapter.submitList(it)
            Log.i("Home Frgament sh8aal for Emsaak", it.toString())
        }
        //call recycler view for كحه
        medicineViewModel.getMedicineForKo7aa()
        medicineViewModel.categories_Ko7aa.observe(viewLifecycleOwner) {
            val adapter = MedicineAdapter()
            binding?.medicineIdRv2?.adapter = adapter
            adapter.submitList(it)
            Log.i("Home Frgament sh8aal for ko7aa", it.toString())
        }
        //call recycler view for مغص
        medicineViewModel.getMedicineForM8aas()
        medicineViewModel.categories_M8aas.observe(viewLifecycleOwner) {
            val adapter = MedicineAdapter()
            binding?.medicineIdRv3?.adapter = adapter
            adapter.submitList(it)
            Log.i("Home Frgament sh8aal", it.toString())
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.medicineIdRv?.adapter = null
        binding?.medicineIdRv2?.adapter = null
        binding?.medicineIdRv3?.adapter = null
    }

    override fun onResume() {
        super.onResume()
        (activity as EshfeenyActivity).View_search_in_fragments(false)
        (activity as EshfeenyActivity).bottomNavigationView(false)
    }
}