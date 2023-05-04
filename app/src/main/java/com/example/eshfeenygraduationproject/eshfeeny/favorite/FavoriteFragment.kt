package com.example.eshfeenygraduationproject.eshfeeny.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.eshfeenygraduationproject.databinding.FragmentFavoriteBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductFavoriteAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel


class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private lateinit var productViewModel: ProductViewModel

    private lateinit var cartProducts: CartResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(inflater)

        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)

        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            val userId = userData._id

            productViewModel.getUserCartItems(userId)
            productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                cartProductsResponse?.let {
                    cartProducts = it
                }
            }

            productViewModel.getFavoriteProducts(userId)

            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->

                stopShimmer()

                if (products != null) {
                    binding?.noItemsLayout?.visibility = View.GONE
                    binding?.favoriteRecyclerView?.visibility = View.VISIBLE

                    val adapter = ProductFavoriteAdapter(productViewModel, userId, cartProducts)
                    binding?.favoriteRecyclerView?.adapter = adapter

                    adapter.submitList(products)
                }
            }
        }
        return binding?.root
    }

    private fun stopShimmer() {
        binding?.shimmerLayoutFavorite?.stopShimmer()
        binding?.shimmerLayoutFavorite?.visibility = View.GONE
        binding?.favoritePage?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}