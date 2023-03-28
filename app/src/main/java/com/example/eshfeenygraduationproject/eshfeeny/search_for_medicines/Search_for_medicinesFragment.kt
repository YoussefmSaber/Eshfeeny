package com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSearchForMedicinesBinding
import com.example.eshfeenygraduationproject.eshfeeny.EshfeenyActivity
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapter
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory


class Search_for_medicinesFragment : Fragment() {
    private var binding:FragmentSearchForMedicinesBinding? = null
    private lateinit var medicineViewModel: MedicineViewModel
    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchForMedicinesBinding.inflate(layoutInflater)
        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)
        medicineViewModel = ViewModelProvider(this, viewModelFactory)[MedicineViewModel::class.java]
        medicineViewModel.getMedicineForVetamenAndMa2kolat()
        medicineViewModel.categories_Emsaak.observe(viewLifecycleOwner) {
            val adapter = MedicineAdapter()
            binding?.medicinesRvId?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding?.medicinesRvId?.adapter = adapter
            adapter.submitList(it)
            Log.i("Emsaak in search medicine fragment", it.toString())
        }
        binding?.chip1?.setOnClickListener {
            medicineViewModel.getMedicineForEmsaak()
            medicineViewModel.categories_Emsaak.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Emsaak in search medicine fragment", it.toString())
            }
        }
        binding?.chip2?.setOnClickListener {
            medicineViewModel.getMedicineForKo7aa()
            medicineViewModel.categories_Ko7aa.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها
        binding?.chip3?.setOnClickListener {
            medicineViewModel.getMedicineForMosknaat()
            medicineViewModel.categories_Mosknaat.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        binding?.chip4?.setOnClickListener {
            medicineViewModel.getMedicineForModat7aywee()
            medicineViewModel.categories_Modat7aywee.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        binding?.chip5?.setOnClickListener {
            medicineViewModel.getMedicineForVetamenAndMa2kolat()
            medicineViewModel.categories_VetamenAndMa2kolat.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip6?.setOnClickListener {
            medicineViewModel.getMedicineFor7modaAndSo2Hadm()
            medicineViewModel.categories_7modaAndSo2Hadm.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip7?.setOnClickListener {
            medicineViewModel.getMedicineForKo7aa()
            medicineViewModel.categories_Ko7aa.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip8?.setOnClickListener {
            medicineViewModel.getMedicineForM8aas()
            medicineViewModel.categories_M8aas.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip9?.setOnClickListener {
            medicineViewModel.getMedicineForEmsaak()
            medicineViewModel.categories_Emsaak.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        binding?.chip10?.setOnClickListener {
            medicineViewModel.getMedicineForKo7aa()
            medicineViewModel.categories_Ko7aa.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip11?.setOnClickListener {
            medicineViewModel.getMedicineForT2wyaaElmna3a()
            medicineViewModel.categories_T2wyaaElmna3a.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip12?.setOnClickListener {
            medicineViewModel.getMedicineForModat7aywee()
            medicineViewModel.categories_Modat7aywee.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }
        //دي مظبوطة بالداتا بتاعتها

        binding?.chip13?.setOnClickListener {
            medicineViewModel.getMedicineForVetamenAndMa2kolat()
            medicineViewModel.categories_VetamenAndMa2kolat.observe(viewLifecycleOwner) {
                val adapter = MedicineAdapter()
                binding?.medicinesRvId?.adapter = adapter
                adapter.submitList(it)
                Log.i("Home Fragment sh8aal for ko7aa", it.toString())
            }
        }




        // Inflate the layout for this fragment
        return binding?.root

    }
    override fun onResume() {
        super.onResume()
        (activity as EshfeenyActivity).bottomNavigationView(true)
        (activity as EshfeenyActivity).View_search_in_fragments(true)
    }
}