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

        binding = FragmentFavoriteBinding.inflate(inflater)
        val repo = MedicineRepoImpl()
        val viewModelFactory = MedicineViewModelFactory(repo)
        medicineViewModel = ViewModelProvider(this, viewModelFactory)[MedicineViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.userData.value?.let {
            Log.i("Favorite Fragment", it._id)
            medicineViewModel.getFavoriteProducts(it._id)
            medicineViewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->
                if (products != null) {
                    binding?.noItemsLayout?.visibility = View.GONE
                    binding?.favoriteRecyclerView?.visibility = View.VISIBLE
                    val adapter = MedicineAdapterFavorite(medicineViewModel, it._id)
                    adapter.submitList(products)
                    binding?.favoriteRecyclerView?.adapter = adapter
                }
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}