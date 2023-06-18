package com.example.eshfeenygraduationproject.authentication.signinFragments.signup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.domain.entity.SendToCheckEmail
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var binding: FragmentSignupBinding? = null
    private lateinit var viewModel: SharedViewModel
    private var loadingDialog: Dialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        binding?.confirmButtonSignup?.setOnClickListener { button ->
            showLoadingDialog()
            val password = binding?.passwordSignup?.text.toString()
            val confPassword = binding?.confirmPassSignup?.text.toString()
            val email = binding?.emailSignup?.text.toString()
            val name = binding?.nameSignup?.text.toString()

            if (password != confPassword) {
                hideLoadingDialog()
                binding?.passwordSignup?.error = getString(R.string.passwordNotMatch)
                binding?.confirmPassSignup?.error = getString(R.string.passwordNotMatch)

            } else {
                val sendEmail = SendToCheckEmail(email)
                viewModel.checkEmailExist(sendEmail)
                Log.i("Test", "No Error Here")
                viewModel.emailFound.observe(viewLifecycleOwner) { emailFound ->
                    Log.i("Test", "No Error Here")
                    if (emailFound.body() != null) {
                        Log.i("Test", "No Error Here")
                        binding?.emailSignupLayout?.error = getString(R.string.emailAlreadyUsed)
                    } else {
                        Log.i("Test", "No Error Here")
                        val action = SignupFragmentDirections.actionSignupFragmentToVerifyFragment(
                            name,
                            email,
                            password
                        )
                        hideLoadingDialog()
                        Navigation.findNavController(button).navigate(action)
                    }
                }
            }
        }

        binding?.createInSignup?.setOnClickListener {
            //action from signup to login
            Navigation.findNavController(it).navigate(R.id.Signup2Login)
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