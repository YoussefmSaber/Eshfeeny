package com.example.eshfeenygraduationproject.eshfeeny.myaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentChangePasswordLoginBinding

class ChangePasswordLogin : Fragment() {

    private var binding: FragmentChangePasswordLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordLoginBinding.inflate(inflater)

        binding?.changePasswordBackButton?.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordLogin_to_myAccountFragment)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}