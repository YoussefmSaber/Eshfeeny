package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentWelcomeBinding.inflate(inflater)

        binding.loginButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_welcomeFragment_to_login)
        }

        binding.signupButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_welcomeFragment_to_signupFragment)
        }

        binding.asGuest.setOnClickListener {
            TODO("To be implemented when making the login functionality")
        }

        return binding.root
    }
}