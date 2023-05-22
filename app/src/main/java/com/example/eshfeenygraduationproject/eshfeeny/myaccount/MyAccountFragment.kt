package com.example.eshfeenygraduationproject.eshfeeny.myaccount

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentMyAccountBinding
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel

class MyAccountFragment : Fragment() {

    private var binding: FragmentMyAccountBinding? = null
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        viewModel.userData.observe(viewLifecycleOwner) {
            binding?.apply {
                nameInputEditText.setText(it.name)
                emailInputEditText.setText(it.email)
                genderInputEditText.setText(it.gender)
                phoneInputEditText.setText(it.phoneNumber)


                isFieldChanged(nameInputEditText, it.name)
                isFieldChanged(emailInputEditText, it.email)
                it.gender?.let { it1 -> isFieldChanged(genderInputEditText, it1) }
                it.phoneNumber?.let { it1 -> isFieldChanged(phoneInputEditText, it1) }


                backButton.setOnClickListener {
                    findNavController().navigate(R.id.action_myAccountFragment_to_moreFragment2)
                }

                changePasswordButton.setOnClickListener {
                    findNavController().navigate(R.id.action_myAccountFragment_to_changePasswordLogin)
                }
            }
        }

        return binding?.root
    }

    private fun isFieldChanged(editText: EditText, initialValue: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != initialValue) {
                    binding?.saveChangesButton?.setBackgroundColor(resources.getColor(R.color.blue_main))
                    binding?.saveChangesButton?.setTextColor(resources.getColor(R.color.white))
                    binding?.saveChangesButton?.isEnabled = true
                } else {
                    binding?.saveChangesButton?.setBackgroundColor(resources.getColor(R.color.button_disabled))
                    binding?.saveChangesButton?.setTextColor(resources.getColor(R.color.text_main))
                    binding?.saveChangesButton?.isEnabled = false
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val genders = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding?.genderInputEditText?.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}