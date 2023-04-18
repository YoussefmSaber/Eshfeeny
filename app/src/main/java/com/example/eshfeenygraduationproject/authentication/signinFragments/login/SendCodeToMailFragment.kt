package com.example.eshfeenygraduationproject.authentication.signinFragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentSendCodeToMailBinding

class SendCodeToMailFragment : Fragment() {

    private var binding: FragmentSendCodeToMailBinding? = null
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSendCodeToMailBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        binding?.sendEmailBtn?.setOnClickListener {
            val action =
                SendCodeToMailFragmentDirections
                    .actionSendCodeToMailFragmentToVerifyCodeLoginFragment(
                        binding?.EnterEmailEditText?.text.toString()
                    )
            Navigation.findNavController(it).navigate(action)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}