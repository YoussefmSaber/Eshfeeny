package com.example.eshfeenygraduationproject.eshfeeny.searchResults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSearchBinding
import com.example.eshfeenygraduationproject.databinding.FragmentSearchResultsBinding

class SearchResultsFragment : Fragment() {

    private var binding: FragmentSearchResultsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultsBinding.inflate(inflater)
        binding?.apply {

        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}