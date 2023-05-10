package com.example.eshfeenygraduationproject.eshfeeny.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentSearchBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory


class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        // Inflate the layout for this fragment
        val repo = ProductRepoImpl()

        val productViewModelFactory = ProductViewModelFactory(repo)
        val viewModel =
            ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

        binding?.searchViewEditText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // perform search using the new text
                val searchText = s.toString()

                var isArabic = false
                var languageName = ""
                for (element in searchText) {
                    if (Character.UnicodeBlock.of(element) == Character.UnicodeBlock.ARABIC) {
                        isArabic = true
                        break
                    }
                }

                viewModel.getSearchResults(searchText)
                viewModel.searchResults.observe(viewLifecycleOwner) {

                    languageName = if (isArabic) {
                        "arabic"
                    } else {
                        "english"
                    }

                    Log.i("search", "$it")
                    val adapter = SearchAdapter(languageName)
                    adapter.submitList(it)
                    binding?.searchResultsRecyclerView?.adapter = adapter
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}