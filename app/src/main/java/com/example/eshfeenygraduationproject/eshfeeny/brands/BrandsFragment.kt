package com.example.eshfeenygraduationproject.eshfeeny.brands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentBrandsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.BrandsAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.BrandsImages


class BrandsFragment : Fragment() {

    private var binding: FragmentBrandsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrandsBinding.inflate(layoutInflater)

        binding?.backButtonBrand?.setOnClickListener {
            findNavController().navigate(R.id.action_brandsFragment_to_homeFragment2)
        }

        val adapter = BrandsAdapter(BrandsImages.brands)
        binding?.brandsRecyclerView?.adapter = adapter

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}