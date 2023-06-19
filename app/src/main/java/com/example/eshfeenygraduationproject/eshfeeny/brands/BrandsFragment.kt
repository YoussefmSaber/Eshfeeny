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
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentBrandsBinding
import com.example.eshfeenygraduationproject.eshfeeny.cameraBottomSheet.ImageBottomSheetFragment
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.BrandsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.search.SearchAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.BrandsImages


class BrandsFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private var binding: FragmentBrandsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrandsBinding.inflate(layoutInflater)

        val medicineRepo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(medicineRepo)
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        binding?.backBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_brandsFragment_to_homeFragment2)
        }

        binding?.searchBar?.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.searchUsingCamera -> {
                    Log.i("image Capture", "Item Clicked")
                    val bottomSheet =
                        ImageBottomSheetFragment("brands")
                    bottomSheet.show(childFragmentManager, "ImageBottomSheetFragment")
                    true
                }

                else -> false
            }
        }

        settingSearch()

        val adapter = BrandsAdapter(BrandsImages.brands)
        binding?.brandsRecyclerView?.adapter = adapter

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