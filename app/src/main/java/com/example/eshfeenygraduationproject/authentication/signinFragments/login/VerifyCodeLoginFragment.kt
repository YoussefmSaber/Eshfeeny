package com.example.eshfeenygraduationproject.authentication.signinFragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.SendToCheckEmail
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentVerifyCodeLoginBinding

class VerifyCodeLoginFragment : Fragment() {

    private var binding: FragmentVerifyCodeLoginBinding? = null
    private val args: VerifyCodeLoginFragmentArgs by navArgs()
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerifyCodeLoginBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        viewModel.verifyCode(args.email)
        binding?.otpCheckButton?.setOnClickListener { button ->
            val inputCode = binding?.otpView?.text.toString()
            viewModel.areCodesTheSame(inputCode)
            viewModel.areBothSame.observe(viewLifecycleOwner) {
                it?.let { same ->
                    if (same) {
                        val checkEmail = SendToCheckEmail(args.email)
                        viewModel.checkEmailExist(checkEmail)
                        viewModel.emailFound.observe(viewLifecycleOwner) { response ->
                            if (response.body() != null) {

                                val action =
                                    VerifyCodeLoginFragmentDirections.actionVerifyCodeLoginFragmentToChangePasswordFragment(
                                        response.body()!!._id
                                    )
                                Navigation.findNavController(button).navigate(action)
                            }
                        }
                    }
                }
            }
        }

        binding?.resendOtpButton?.setOnClickListener {
            viewModel.verifyCode(args.email)
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}