package com.example.eshfeenygraduationproject.eshfeeny.brands

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.BrandItemsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.search.SearchAdapter

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
        settingSearch()
        binding?.searchBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searchUsingCamera -> {
                    Log.i("image Capture", "Item Clicked")
                    val bottomSheet =
                        ImageBottomSheetFragment("brandItems")
                    bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
                    true
                }

                else -> false
            }
        }

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
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

            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->

                productViewModel.getBrandItems(args.brandName)
                productViewModel.brandItems.observe(viewLifecycleOwner) {
                    val adapter =
                        userId?.let { it1 ->
                            BrandItemsAdapter(productViewModel,
                                it1, favoriteProducts, cartProducts)
                        }
                    binding?.brandsRecyclerView?.adapter = adapter

                    adapter?.submitList(it)
                }
            }
        }

        binding?.backBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_brandItemsFragment_to_brandsFragment)
        }

        return binding?.root
    }

    private fun settingSearch() {
        binding?.searchViewText?.editText?.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // perform search using the new text
                    val searchText = s.toString()

                    productViewModel.getSearchResults(searchText)
                    productViewModel.searchResults.observe(viewLifecycleOwner) {

                        val adapter = SearchAdapter("home")
                        adapter.submitList(it)
                        binding?.searchResultsRecyclerView?.adapter = adapter
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}