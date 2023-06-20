package com.example.eshfeenygraduationproject.authentication.signinFragments.login

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
import androidx.navigation.Navigation
import com.example.domain.entity.VerifyLoginResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentLoginBinding
import com.example.eshfeenygraduationproject.eshfeeny.MainActivity

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: SharedViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        binding?.confirmButtonSignin?.setOnClickListener {
            showLoadingDialog()

            val userData = VerifyLoginResponse(
                binding?.nameSignin?.text.toString(),
                binding?.passwordSignup?.text.toString()
            )

            viewModel.verifyLogin(userData)
            viewModel.verifyUserLogin.observe(viewLifecycleOwner) {

                if (it != null) {
                    Log.i("login", "${it.body()} + ${it.code()}")
                }
                if (it != null) {
                    if (it.body() != null) {
                        hideLoadingDialog()
                        Log.i("login", it.body().toString())
                        Toast.makeText(
                            requireContext(),
                            "You have been logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        it.body()?.let { userInfo ->
                            Log.i("login", userInfo.toString())
                            userInfo.state = "user"
                            viewModel.addUserToDatabase(userInfo)
                            Log.i("DB", userInfo.toString())
                            val intent = Intent(
                                activity,
                                MainActivity::class.java
                            )
                            Log.i("Login", intent.toString())
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    } else {
                        hideLoadingDialog()
                        Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding?.forgetPasswordEditText?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_sendCodeToMailFragment)
        }

        binding?.createInSignin?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.Login2Signup)
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}