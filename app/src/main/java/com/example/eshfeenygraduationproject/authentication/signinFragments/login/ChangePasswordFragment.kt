package com.example.eshfeenygraduationproject.authentication.signinFragments.login

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.entity.patchRequestVar.ChangePassword
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    private var binding: FragmentChangePasswordBinding? = null
    private val args: ChangePasswordFragmentArgs by navArgs()
    private lateinit var viewModel: SharedViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        binding?.confBtn?.setOnClickListener {
            showLoadingDialog()
            val newPassword = ChangePassword(binding?.newPasswordEditText?.text.toString())
            val newPasswordConf = ChangePassword(binding?.confNewPasswordEditText?.text.toString())
            Log.i("password", newPassword.toString())
            Log.i("password", "$newPasswordConf confpassword")

            if (newPassword.password == newPasswordConf.password) {
                hideLoadingDialog()
                viewModel.updateUserPassword(args.userId, newPassword)
                Log.i("password", "password is changed")
                Toast.makeText(requireContext(), "Password is changed", Toast.LENGTH_SHORT).show()
                val action =
                    ChangePasswordFragmentDirections.actionChangePasswordFragmentToLoginFragment()
                findNavController().navigate(action)
            } else {
                hideLoadingDialog()
                binding?.passwordNotMatch?.visibility = View.VISIBLE
            }
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