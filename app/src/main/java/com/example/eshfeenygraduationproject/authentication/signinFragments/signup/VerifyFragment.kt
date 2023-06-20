package com.example.eshfeenygraduationproject.authentication.signinFragments.signup

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.domain.entity.CreateUser
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentVerifyBinding
import com.example.eshfeenygraduationproject.eshfeeny.MainActivity

class VerifyFragment : Fragment() {

    private val args: VerifyFragmentArgs by navArgs()

    private var binding: FragmentVerifyBinding? = null
    private lateinit var viewModel: SharedViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerifyBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        val newUser = CreateUser(
            name = args.newUserName,
            email = args.newUserEmail,
            password = args.newUserPassword
        )

        Log.i("user data: ", "verify Fragment: $newUser")
        viewModel.verifyCode(newUser.email)

        binding?.otpCheckButton?.setOnClickListener {
            showLoadingDialog()
            Log.i("Verify code: ", "Clicked")

            val inputCode = binding?.otpView?.text.toString()

            viewModel.areCodesTheSame(inputCode)
            viewModel.areBothSame.observe(viewLifecycleOwner) {
                if (it) {
                    hideLoadingDialog()
                    Toast.makeText(
                        requireContext(),
                        "You have verified your account",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.createNewUser(newUser)
                    viewModel.createUserResponse.observe(viewLifecycleOwner) { response ->
                        response.body()?.let { userData ->
                            Log.i("DB Singup", userData.toString())
                            userData.password = args.newUserPassword
                            userData.state = "user"
                            viewModel.addUserToDatabase(userData)
                            val intent = Intent(
                                activity,
                                MainActivity::class.java
                            )
                            startActivity(intent)
                        }
                    }
                } else {
                    hideLoadingDialog()
                    binding?.otpWrongMessage?.visibility = View.VISIBLE
                }
            }
        }
        binding?.resendOtpButton?.setOnClickListener {
            viewModel.verifyCode(newUser.email)
        }

        return binding?.root
    }


    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(requireContext())
            loadingDialog!!.setContentView(R.layout.loading_dialog)
            loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog!!.setCancelable(false)
        }
        loadingDialog!!.show()
    }

    private fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}