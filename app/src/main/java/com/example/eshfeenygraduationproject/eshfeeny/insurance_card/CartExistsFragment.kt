package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentCartExistsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.InsuranceCardAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel


class CartExistsFragment : Fragment() {
    private var binding: FragmentCartExistsBinding? = null
    private lateinit var userViewModel: UserViewModel
    private val args: CartExistsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartExistsBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner) { userDetails ->
            userDetails._id?.let { userViewModel.getInsuranceCards(it) }
            userViewModel.insuranceCardItems.observe(viewLifecycleOwner) {
                Log.d("Insurance Card", it.toString())
                val adapter = InsuranceCardAdapter()
                adapter.submitList(it)
                binding?.existsCardRvId?.adapter = adapter
            }
        }
        when (args.insuranceCardName) {
            "EgyCare" -> binding?.tV2?.text = "EGYCARE"
            "MetLife" -> binding?.tV2?.text = "MetLife"
            "Misr" -> binding?.tV2?.text = "Misr"
            else -> Log.i("insuranceCard", "Error unknown entry")
        }

        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_cartExistsFragment_to_servicesInsuranceCardFragment)
        }
        binding?.addCartExistsBtn?.setOnClickListener {
            val action =
                CartExistsFragmentDirections.actionCartExistsFragmentToInfoInsuranceCardFragment(
                    args.insuranceCardName
                )
            findNavController().navigate(action)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

}