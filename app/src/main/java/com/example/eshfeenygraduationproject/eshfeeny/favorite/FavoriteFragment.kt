package com.example.eshfeenygraduationproject.eshfeeny.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentFavoriteBinding
import com.example.eshfeenygraduationproject.eshfeeny.medicine.MedicineAdapterFavorite
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.viewmodel.UserViewModel


class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(inflater)

        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)

        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner) {
            val userId = it._id

            productViewModel.getFavoriteProducts(userId)

            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->

                if (products != null) {
                    binding?.noItemsLayout?.visibility = View.GONE
                    binding?.favoriteRecyclerView?.visibility = View.VISIBLE

                    val adapter = MedicineAdapterFavorite(productViewModel, userId)
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