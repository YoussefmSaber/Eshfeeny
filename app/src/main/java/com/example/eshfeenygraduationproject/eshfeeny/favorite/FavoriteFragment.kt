package com.example.eshfeenygraduationproject.eshfeeny.favorite

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentFavoriteBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductFavoriteAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory


class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null
    private lateinit var productViewModel: ProductViewModel
    private var loadingDialog: Dialog? = null
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
            if (userData.state != "guest") {
                val userId = userData._id

                if (userId != null) {
                    productViewModel.getUserCartItems(userId)
                }
                productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                    cartProductsResponse?.let {
                        cartProducts = it
                    }
                }

                if (userId != null) {
                    productViewModel.getFavoriteProducts(userId)
                }

                productViewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->

                    stopShimmer()

                    if (products != null) {
                        binding?.noItemsLayout?.visibility = View.GONE
                        binding?.favoriteRecyclerView?.visibility = View.VISIBLE

                        val adapter =
                            userId?.let {
                                ProductFavoriteAdapter(
                                    productViewModel,
                                    it,
                                    cartProducts
                                )
                            }
                        binding?.favoriteRecyclerView?.adapter = adapter

                        adapter?.submitList(products)
                    }
                }
            } else {
                showLoadingDialog()
            }
        }
        return binding?.root
    }

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(requireContext())
            loadingDialog!!.setContentView(R.layout.guest_warning)
            loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog!!.setCancelable(true)
        }
        loadingDialog!!.show()
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