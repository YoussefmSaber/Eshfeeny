package com.example.eshfeenygraduationproject.eshfeeny.more

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentMoreBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel

class MoreFragment : Fragment() {

    private var binding: FragmentMoreBinding? = null
    private var loadingDialog: Dialog? = null
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.userData.observe(viewLifecycleOwner) {
            if (it.state != "guest") {
                binding?.myAccount?.setOnClickListener {
                    findNavController().navigate(R.id.action_moreFragment2_to_myAccountFragment)
                }
                binding?.Alarm?.setOnClickListener {
                    findNavController().navigate(R.id.action_moreFragment2_to_alarmFragment)
                }
                binding?.BmiBmr?.setOnClickListener {
                    findNavController().navigate(R.id.action_moreFragment2_to_bmiAndBmrFragment)
                }
            } else {
                binding?.myAccount?.setOnClickListener {
                    showLoadingDialog()
                }
                binding?.Alarm?.setOnClickListener {
                    showLoadingDialog()
                }
                binding?.BmiBmr?.setOnClickListener {
                    showLoadingDialog()
                }
            }
        }

        binding?.logoutButton?.setOnClickListener {
            val bottomSheet =
                LogoutBottomSheetFragment(viewModel)
            bottomSheet.show(childFragmentManager, "LogoutBottomSheetFragment")
        }

        return binding?.root
    }
    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(requireContext())
            loadingDialog!!.setContentView(R.layout.guest_warning)
            loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog!!.setCancelable(true)
        }
        loadingDialog!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}