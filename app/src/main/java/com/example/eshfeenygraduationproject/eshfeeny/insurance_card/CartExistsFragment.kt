package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.repository.ProductRepoImpl
import com.example.data.repository.UserRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentCartExistsBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.InsuranceCardAdapter
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel


class CartExistsFragment : Fragment() {
    private var binding:FragmentCartExistsBinding? = null
    private lateinit var userViewModel: UserViewModel
    private val args: CartExistsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartExistsBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.userData.observe(viewLifecycleOwner){ userDetails ->
            userViewModel.getInsuranceCards(userDetails._id)
            userViewModel.insuranceCardItems.observe(viewLifecycleOwner){
                val adapter = InsuranceCardAdapter()
                adapter.submitList(it.insuranceCard)
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
            findNavController().navigate(R.id.action_cartExistsFragment_to_infoInsuranceCardFragment)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

}