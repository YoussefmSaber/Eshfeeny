package com.example.eshfeenygraduationproject.eshfeeny.search_for_medicines

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentMedicineCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.MedicinsCategories
import com.example.eshfeenygraduationproject.eshfeeny.util.MedicinsCategories.dentalCare
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory
import com.google.android.material.chip.Chip

class MedicineCategoryFragment : Fragment() {

    private var selectedChip: Chip? = null
    private var binding: FragmentMedicineCategoryBinding? = null
    private val args: MedicineCategoryFragmentArgs by navArgs()
    private lateinit var viewModel: MedicineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMedicineCategoryBinding.inflate(inflater)

        binding?.medicineRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[MedicineViewModel::class.java]

        binding?.backBtn?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_medicineCategoryFragment_to_homeFragment2)
        }

        when (args.category) {
            "allMeds" -> {
                binding?.categoryTitle?.text = "كل الادوية"
                setAllMeds()
            }
            "virusProtection" -> {
                binding?.categoryTitle?.text = "الحماية من الفيروسات"
                setVirusProtection()
            }
            "motherAndChild" -> {

            }
            "womenProducts" -> {

            }
            "skinAndHairCare" -> {

            }
            "dentalCareBtn" -> {

            }
            "menProducts" -> {

            }
        }

        return binding?.root
    }

    private fun setVirusProtection() {
        for (med in MedicinsCategories.virusProtection) {
            val newChip = createChip(getString(med))
            binding?.medicineChipGroup?.addView(newChip)
        }
    }

    private fun setAllMeds() {
        var isFirstChip = true
        for (med in MedicinsCategories.allMedicines) {
            val newChip = createChip(getString(med))
            binding?.medicineChipGroup?.addView(newChip)

            if (isFirstChip) {
                newChip.isSelected = true
                setChipColors(newChip)

                selectedChip = newChip
                isFirstChip = false

                viewModel.getMedicineForAllMedicines()
                viewModel.categories_AllMedicines.observe(viewLifecycleOwner) { response ->

                    Log.i("chip Test", response.toString())
                    val adapter = MedicineAdapter()

                    adapter.submitList(response)
                    binding?.medicineRecyclerView?.adapter = adapter
                }
            }
        }
    }

    private fun createChip(name: String): Chip {
        val chip = Chip(context)
        chip.text = name

        setChipColors(chip)

        chip.setOnClickListener {
            if (selectedChip != it) {
                // Deselect the previously selected chip
                selectedChip?.let { previousSelectedChip ->

                    previousSelectedChip.isSelected = false
                    setChipColors(previousSelectedChip)
                }

                // Select the clicked chip
                chip.isSelected = true
                setChipColors(chip)

                // Update the currently selected chip
                selectedChip = chip


                viewModel.getMedicinesFromRemote(name)
                viewModel.category_medicines.observe(viewLifecycleOwner) { response ->

                    val adapter = MedicineAdapter()
                    adapter.submitList(response.body())

                    binding?.medicineRecyclerView?.adapter = adapter
                }
                if (chip.text == getString(R.string.allMedicines)) {
                    viewModel.getMedicineForAllMedicines()
                    viewModel.categories_AllMedicines.observe(viewLifecycleOwner) { response ->
                        Log.i("chip Test", response.toString())
                        val adapter = MedicineAdapter()
                        adapter.submitList(response)
                        binding?.medicineRecyclerView?.adapter = adapter
                    }
                }
            }
        }

        return chip
    }

    private fun setChipColors(chip: Chip) {
        val textColor = if (chip.isSelected) R.color.blue_main else R.color.text_color
        val bgColor = if (chip.isSelected) R.color.chips_color2 else R.color.chips_color
        val strokeColor =
            if (chip.isSelected) R.color.chip_stroke_selected else R.color.chip_stroke_notSelected

        chip.setTextColor(ContextCompat.getColor(requireContext(), textColor))
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), bgColor))
        chip.chipStrokeColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), strokeColor))

        chip.chipStrokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            context?.resources?.displayMetrics
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}