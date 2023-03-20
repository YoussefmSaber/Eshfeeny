package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.CreateUser
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModelFactory
import com.example.eshfeenygraduationproject.databinding.FragmentVerifyBinding

class VerifyFragment : Fragment() {

    val args: VerifyFragmentArgs by navArgs()

    private var binding: FragmentVerifyBinding? = null
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerifyBinding.inflate(inflater)
        val repository = UserRepoImpl()
        val viewModelFactory = SharedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]
        val newUser = CreateUser(
            args.newUserName,
            args.newUserEmail,
            args.newUserPassword
        )

        Log.i("user data: ", "verify Fragment: $newUser")
        viewModel.verifyNewUser(newUser.email)

        binding?.otpCheckButton?.setOnClickListener {
            Log.i("Verify code: ", "Clicked")

            val receivedCode =  viewModel.verifyNewAccountResponse.value?.code

            val inputCode = binding?.otpView?.text.toString()
            val receivedCodeType = receivedCode?.javaClass
            val inputCodeType = inputCode.javaClass

            viewModel.areCodesTheSame(inputCode)
            viewModel.areBothSame.observe(viewLifecycleOwner){
                Log.i("Verify code: ", "button Clicked")
                Log.i(
                    "Verify code: ",
                    "Input field code $inputCode and it's type $inputCodeType"
                )
                Log.i(
                    "Verify code: ",
                    "received code value: $receivedCode and it's type $receivedCodeType"
                )
                Log.i(
                    "Verify code: ",
                    "Are both equal " + viewModel.areBothSame.value
                )
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        "You have verified your account",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("Verify", "you have been verified")

                    viewModel.createNewUser(newUser)
                } else {
                    binding?.otpView?.setLineColor(getResources().getColor(R.color.red_text))
                    binding?.otpView?.error = "الرمز الذي قمت بادخاله خاطئ. برجاء المحاولة مره اخري"
                }
            }
        }

        binding?.resendOtpButton?.setOnClickListener {
            viewModel.verifyNewUser(newUser.email)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}