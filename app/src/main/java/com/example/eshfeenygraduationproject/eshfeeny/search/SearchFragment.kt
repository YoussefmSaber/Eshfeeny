package com.example.eshfeenygraduationproject.eshfeeny.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ProductRepoImpl
import com.example.eshfeenygraduationproject.databinding.FragmentSearchBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModelFactory.ProductViewModelFactory


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

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}