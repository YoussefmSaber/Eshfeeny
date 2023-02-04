package com.example.eshfeenygraduationproject.authentication.fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        binding.passwordSignup.hint = getString(R.string.pass)
        binding.nameSignin.hint = getString(R.string.Email)

        setHint(binding.nameSignin)
        setHint(binding.passwordSignup, binding.PasswordSignup)

        binding.createInSignin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragmen_to_signupFragment)
        }
        return binding.root
    }

    fun setHint(view: TextInputEditText, parent: TextInputLayout) {
        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                parent.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE)
                view.hint = ""
                (v as TextInputEditText).gravity = Gravity.LEFT
            } else {
                parent.setEndIconMode(TextInputLayout.END_ICON_NONE)
                if (view.text?.isEmpty() == true) {
                    view.hint = getString(R.string.pass)
                    (v as TextInputEditText).gravity = Gravity.RIGHT
                }
            }
        }
    }

    fun setHint(view: TextInputEditText) {
        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                view.hint = ""
                (v as TextInputEditText).gravity = Gravity.LEFT
            } else {
                if (view.text?.isEmpty() == true) {
                    view.hint = getString(R.string.Email)
                    (v as TextInputEditText).gravity = Gravity.RIGHT
                }
            }
        }
    }

}