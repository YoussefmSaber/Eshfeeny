package com.example.eshfeenygraduationproject.eshfeeny.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapter
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var  medicineViewModel:MedicineViewModel

    private var binding: FragmentHomeBinding? = null

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val adapter = MedicineAdapter()

        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)

        medicineViewModel = ViewModelProvider(this,viewModelFactory)[MedicineViewModel::class.java]
        //call recycler view for امساك
        medicineViewModel.getMedicineForEmsaak()
        medicineViewModel.categories.observe(viewLifecycleOwner){category ->
            binding?.medicineIdRv?.adapter = adapter
            adapter.submitList(category)
            Log.i("Home Frgament sh8aal for Emsaak",category.toString())
        }
        //call recycler view for كحه
        medicineViewModel.getMedicineForKo7aa()
        medicineViewModel.categories.observe(viewLifecycleOwner){category ->
            binding?.medicineIdRv2?.adapter = adapter
            adapter.submitList(category)
            Log.i("Home Frgament sh8aal for ko7aa",category.toString())
        }
        //call recycler view for مغص
        medicineViewModel.getMedicineForM8aas()
        medicineViewModel.categories.observe(viewLifecycleOwner){category ->
            binding?.medicineIdRv3?.adapter = adapter
            adapter.submitList(category)
            Log.i("Home Frgament sh8aal",category.toString())
        }
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding?.medicineIdRv?.adapter = null
        binding?.medicineIdRv2?.adapter = null
        binding?.medicineIdRv3?.adapter = null
    }
}