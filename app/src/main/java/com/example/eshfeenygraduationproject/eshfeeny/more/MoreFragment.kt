package com.example.eshfeenygraduationproject.eshfeeny.more

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.AuthenticationActivity
import com.example.eshfeenygraduationproject.databinding.FragmentMoreBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel

class MoreFragment : Fragment() {

    private var binding: FragmentMoreBinding? = null

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding?.myAccount?.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment2_to_myAccountFragment)
        }

        binding?.Alarm?.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment2_to_alarmFragment)
        }
        binding?.BmiBmr?.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment2_to_bmiAndBmrFragment)
        }
        binding?.logoutButton?.setOnClickListener {
            viewModel.deleteUserFromDatabase()
            val intent = Intent(
                activity,
                AuthenticationActivity::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return binding?.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}