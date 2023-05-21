package com.example.eshfeenygraduationproject.eshfeeny.myaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentMyAccountBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel

class MyAccountFragment : Fragment() {

    private var binding: FragmentMyAccountBinding? = null
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        viewModel.userData.observe(viewLifecycleOwner) {
            binding?.nameInputEditText?.setText(it.name)
            binding?.emailInputEditText?.setText(it.email)
            binding?.genderInputEditText?.setText(it.gender)
            binding?.phoneInputEditText?.setText(it.phoneNumber)
        }



        binding?.changePasswordButton?.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_changePasswordLogin)
        }

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        val genders = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding?.genderInputEditText?.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}