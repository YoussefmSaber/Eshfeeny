package com.example.eshfeenygraduationproject.welcome.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSignupBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)

        setHint(binding.nameSignup, R.string.userName)
        setHint(binding.emailSignup, R.string.Email)
        setHint(binding.passwordSignup, binding.PasswordSignup, R.string.pass)
        setHint(binding.confirmPassSignup, binding.ConfirmPassSignup, R.string.confPass)

        binding.createInSignup.setOnClickListener{
            //action from signup to login
            Navigation.findNavController(it).navigate(R.id.action_signupFragment_to_loginFragmen)
        }
        return binding.root
    }

    fun setHint(view: TextInputEditText, parent: TextInputLayout, string: Int) {

        view.hint = getString(string)

        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                parent.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE)
                view.hint = ""
                (v as TextInputEditText).gravity = Gravity.LEFT
            } else {
                parent.setEndIconMode(TextInputLayout.END_ICON_NONE)
                if (view.text?.isEmpty() == true) {
                    view.hint = getString(string)
                    (v as TextInputEditText).gravity = Gravity.RIGHT
                }
            }
        }
    }

    fun setHint(view: TextInputEditText, string: Int) {

        view.hint = getString(string)

        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                view.hint = ""
                (v as TextInputEditText).gravity = Gravity.LEFT
            } else {
                if (view.text?.isEmpty() == true) {
                    view.hint = getString(string)
                    (v as TextInputEditText).gravity = Gravity.RIGHT
                }
            }
        }
    }
}