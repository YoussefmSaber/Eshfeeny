package com.example.eshfeenygraduationproject.eshfeeny.searchResults

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSearchResultsBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.SearchResultsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.search.SearchAdapter

class SearchResultsFragment : Fragment() {

    private var binding: FragmentSearchResultsBinding? = null
    private val args: SearchResultsFragmentArgs by navArgs()
    private lateinit var productViewModel: ProductViewModel
    private lateinit var userViewModel: UserViewModel
    private var cartItems: CartResponse? = null
    private var favoriteItem: ProductResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultsBinding.inflate(inflater)

        binding?.searchBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searchUsingCamera -> {
                    Log.i("image Capture", "Item Clicked")
                    val bottomSheet =
                        ImageBottomSheetFragment("search")
                    bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
                    true
                }

                else -> false
            }
        }
        binding?.apply {

            this.searchResultsImageRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            setupViewModel()
            navigate2HomeFragment()
            settingSearch()
            productViewModel.getImageUrl(args.imageUrl)
            Log.i("search Result", args.imageUrl)
            productViewModel.imageSearchResults.observe(viewLifecycleOwner) {
                userViewModel.userData.observe(viewLifecycleOwner) { userData ->
                    if (userData.state != "guest") {
                        userData._id?.let { it1 -> productViewModel.getUserCartItems(it1) }
                        productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                            cartItems = cartProductsResponse
                            userData._id?.let { it1 -> productViewModel.getFavoriteProducts(it1) }
                            productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProductsResponse ->
                                favoriteItem = favoriteProductsResponse
                            }
                        }
                    }
                    val adapter = SearchResultsAdapter(
                        productViewModel,
                        userData._id,
                        favoriteItem,
                        cartItems,
                        userData.state
                    )

                    this.searchResultsImageRecyclerView.adapter = adapter
                    adapter.submitList(it)
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

    private fun settingSearch() {
        binding?.searchViewText?.editText?.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // do nothing
                }

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

                override fun afterTextChanged(s: Editable?) {

                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}