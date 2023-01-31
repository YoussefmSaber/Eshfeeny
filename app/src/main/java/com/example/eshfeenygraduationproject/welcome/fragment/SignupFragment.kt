package com.example.eshfeenygraduationproject.welcome.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSignupBinding


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)
        binding.createInSignup.setOnClickListener{
            //action from signup to login
            Navigation.findNavController(it).navigate(R.id.action_signupFragment_to_loginFragmen)
        }
        return binding.root
    }

}