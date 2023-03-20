package com.example.eshfeenygraduationproject.eshfeeny.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.repository.MedicineRepoImpl
import com.example.domain.entity.CategoryResponseItem
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapter
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var  medicineViewModel:MedicineViewModel

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val adapter = MedicineAdapter()

        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)

        medicineViewModel = ViewModelProvider(this,viewModelFactory)[MedicineViewModel::class.java]
        medicineViewModel.getMedicine()
        medicineViewModel.categories.observe(viewLifecycleOwner){category ->
            binding?.medicineIdRv?.adapter = adapter
            adapter.submitList(category.result)
            Log.i("Home Frgament sh8aal",category.toString())
        }
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding?.medicineIdRv?.adapter = null
    }
}