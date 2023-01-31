package com.example.eshfeenygraduationproject.welcome.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            TODO("connect with login page")
        }

        binding.signupButton.setOnClickListener {
            TODO("connect with signup page")
        }

        binding.asGuest.setOnClickListener {
            TODO("To be implemented when making the login functionality")
        }

        return binding.root
    }
}