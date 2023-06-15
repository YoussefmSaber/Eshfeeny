package com.example.eshfeenygraduationproject.eshfeeny.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.authentication.AuthenticationActivity
import com.example.eshfeenygraduationproject.databinding.FragmentLogoutBottomSheetBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LogoutBottomSheetFragment(val viewModel: UserViewModel) : BottomSheetDialogFragment() {

    private var binding: FragmentLogoutBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogoutBottomSheetBinding.inflate(inflater)

        binding?.apply {
            this.signoutButton.setOnClickListener {
                viewModel.deleteUserFromDatabase()
                val intent = Intent(
                    activity,
                    AuthenticationActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            this.cancelButton.setOnClickListener {
                dismiss()
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}