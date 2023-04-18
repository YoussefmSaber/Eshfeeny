package com.example.eshfeenygraduationproject.eshfeeny.favorite

import android.hardware.lights.Light
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.MedicineRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentFavoriteBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapterFavorite
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.MedicineViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.UserViewModel


class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private lateinit var medicineViewModel: MedicineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.i("favoriteFragment", "View Created")
        binding = FragmentFavoriteBinding.inflate(inflater)
        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)
        medicineViewModel = ViewModelProvider(this, viewModelFactory)[MedicineViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        Log.i("favoriteFragment", "ViewModel Initialized")


        userViewModel.userData.observe(viewLifecycleOwner) {
            val userId = it._id

            medicineViewModel.getFavoriteProducts(userId)

            medicineViewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->

                if (products != null) {
                    binding?.noItemsLayout?.visibility = View.GONE
                    binding?.favoriteRecyclerView?.visibility = View.VISIBLE

                    val adapter = MedicineAdapterFavorite(medicineViewModel, userId)
                    binding?.favoriteRecyclerView?.adapter = adapter

                    adapter.submitList(products)
                }
            }
        }



        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}