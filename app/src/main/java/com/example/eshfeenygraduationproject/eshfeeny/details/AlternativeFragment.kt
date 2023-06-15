package com.example.eshfeenygraduationproject.eshfeeny.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.databinding.FragmentAlternativeBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCategoryAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AlternativeFragment(
    private val productId: String,
    private val productViewModel: ProductViewModel,
    val userId: String,
) : BottomSheetDialogFragment() {

    private var binding: FragmentAlternativeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlternativeBinding.inflate(inflater)

        productViewModel.getAlternativeProducts(productId)
        productViewModel.alternativeProducts.observe(viewLifecycleOwner) { alternativeProducts ->

            productViewModel.getFavoriteProducts(userId)
            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                productViewModel.getUserCartItems(userId)
                productViewModel.cartItems.observe(viewLifecycleOwner) { cartResponse ->
                    val adapter =
                        ProductCategoryAdapter(
                            productViewModel,
                            userId,
                            favoriteProducts,
                            cartResponse
                        )
                    binding?.alternativeProductsRecyclerView?.adapter = adapter
                    adapter.submitList(alternativeProducts)
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