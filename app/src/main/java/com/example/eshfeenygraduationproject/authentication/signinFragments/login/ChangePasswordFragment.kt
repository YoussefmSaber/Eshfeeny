package com.example.eshfeenygraduationproject.authentication.signinFragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.data.repository.UserRepoImpl
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModelFactory
import com.example.eshfeenygraduationproject.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    private var binding: FragmentChangePasswordBinding? = null
    private val args: ChangePasswordFragmentArgs by navArgs()
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater)

        val repository = UserRepoImpl()
        val viewModelFactory = SharedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        binding?.confBtn?.setOnClickListener {
            val newPassword = binding?.newPasswordTextview?.text.toString()
            val newPasswordConf = binding?.confNewPasswordEditText?.text.toString()

            if (newPassword == newPasswordConf) {
                viewModel.updateUserPassword(args.userId, newPassword)
            } else {
                binding?.passwordNotMatch?.visibility = View.VISIBLE
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}