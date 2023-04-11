package com.example.eshfeenygraduationproject.eshfeeny.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentDetailsBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.SideEffectsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.medicine.UsageAdapter
import com.example.eshfeenygraduationproject.eshfeeny.medicine.UseCaseAdapter
import com.example.eshfeenygraduationproject.eshfeeny.medicine.WarningAdapter
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.DetailsViewModel


class DetailsFragment : Fragment() {
    private var binding:FragmentDetailsBinding? = null
    private lateinit var viewModel: DetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        binding?.lifecycleOwner = viewLifecycleOwner

        // for call Use Case RV
        viewModel.medicine.observe(viewLifecycleOwner){
            val useCaseAdapter = UseCaseAdapter()
            binding?.idRvUseCaseDetails?.adapter = useCaseAdapter
            useCaseAdapter.submitList(it)
        }

        // for Usage RV
        viewModel.medicine.observe(viewLifecycleOwner){
            val usageAdapter = UsageAdapter()
            binding?.idRvUseCaseDetails?.adapter = usageAdapter
            usageAdapter.submitList(it)
        }

        // for Side Effect RV
        viewModel.medicine.observe(viewLifecycleOwner){
            val sideEffectsAdapter = SideEffectsAdapter()
            binding?.idRvUseCaseDetails?.adapter = sideEffectsAdapter
            sideEffectsAdapter.submitList(it)
        }

        // for Warning RV
        viewModel.medicine.observe(viewLifecycleOwner){
            val warningAdapter = WarningAdapter()
            binding?.idRvUseCaseDetails?.adapter = warningAdapter
            warningAdapter.submitList(it)
        }


        // Inflate the layout for this fragment
        return binding?.root
    }

}