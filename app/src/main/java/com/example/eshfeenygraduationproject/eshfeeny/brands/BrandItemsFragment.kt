package com.example.eshfeenygraduationproject.eshfeeny.brands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentBrandsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.BrandItemsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory

class BrandItemsFragment : Fragment() {

    private val args: BrandItemsFragmentArgs by navArgs()
    private var binding: FragmentBrandsBinding? = null

    private lateinit var productViewModel: ProductViewModel

    private lateinit var cartProducts: CartResponse
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBrandsBinding.inflate(inflater)

        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)

        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding?.brandsTitle?.text = args.brandName

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            val userId = userData._id

            productViewModel.getUserCartItems(userId)
            productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                cartProductsResponse?.let {
                    cartProducts = it
                }
            }

            productViewModel.getFavoriteProducts(userId)

            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                productViewModel.getBrandItems(args.brandName)
                productViewModel.brandItems.observe(viewLifecycleOwner) {
                    val adapter =
                        BrandItemsAdapter(productViewModel, userId, favoriteProducts, cartProducts)
                    binding?.brandsRecyclerView?.adapter = adapter

                    adapter.submitList(it)
                }
            }
        }

        binding?.backButtonBrand?.setOnClickListener {
            findNavController().navigate(R.id.action_brandItemsFragment_to_brandsFragment)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}