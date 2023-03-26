package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    // Initializing the binding variable to null
    private var binding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // giving the binding variable it's value
        binding = FragmentWelcomeBinding.inflate(inflater)
        // setting a navigation to the buttons to change the page
        binding?.loginButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.Welcome2Login)
        }
        binding?.signupButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.Welcome2Signup)
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}