package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.data.local.db.user.model.UserInfo
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentWelcomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.MainActivity

class WelcomeFragment : Fragment() {
    // Initializing the binding variable to null
    private var binding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        // giving the binding variable it's value
        binding = FragmentWelcomeBinding.inflate(inflater)
        // setting a navigation to the buttons to change the page
        binding?.loginButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.Welcome2Login)
        }
        binding?.signupButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.Welcome2Signup)
        }
        binding?.asGuest?.setOnClickListener {
            val userData = UserInfo(0)
            viewModel.addUserToDatabase(userData)
            val intent = Intent(
                activity,
                MainActivity::class.java
            )
            Log.i("Login", intent.toString())
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}