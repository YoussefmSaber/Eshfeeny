package com.example.eshfeenygraduationproject.eshfeeny.searchResults

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSearchBinding
import com.example.eshfeenygraduationproject.databinding.FragmentSearchResultsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.SearchResultsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.searchForProducts.ProductCategoryFragmentArgs

class SearchResultsFragment : Fragment() {

    private var binding: FragmentSearchResultsBinding? = null
    private val args: SearchResultsFragmentArgs by navArgs()
    private lateinit var productViewModel: ProductViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultsBinding.inflate(inflater)
        binding?.apply {

            this.searchResultsImageRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            setupViewModel()
            navigate2HomeFragment()

            productViewModel.getImageUrl(args.imageUrl)
            Log.i("search Result", args.imageUrl)
            productViewModel.imageSearchResults.observe(viewLifecycleOwner) {
                userViewModel.userData.observe(viewLifecycleOwner) { userData ->
                    productViewModel.getUserCartItems(userData._id)
                    productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->

                        productViewModel.getFavoriteProducts(userData._id)
                        productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProductsResponse ->

                            val adapter = SearchResultsAdapter(
                                productViewModel,
                                userData._id,
                                favoriteProductsResponse,
                                cartProductsResponse
                            )

                            Log.i("Search Result products", it.toString())

                            this.searchResultsImageRecyclerView.adapter = adapter
                            adapter.submitList(it)
                        }
                    }
                }
            }
        }

        return binding?.root
    }


    private fun setupViewModel() {
        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)
        productViewModel =
            ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun navigate2HomeFragment() {
        binding?.backBtn?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_searchResultsFragment_to_homeFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}