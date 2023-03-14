package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentLoginBinding
import com.google.android.material.textfield.*


class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        binding?.passwordSignup?.hint = getString(R.string.pass)
        binding?.nameSignin?.hint = getString(R.string.Email)

        binding?.nameSignin?.let {
            setHint(it)
        }
        binding?.passwordSignup?.let {
            binding?.PasswordSignup?.let { it1 ->
                setHint(it, it1)
            }
        }

        binding?.createInSignin?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.Login2Signup)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    private fun setHint(view: TextInputEditText, parent: TextInputLayout) {
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

    private fun setHint(view: TextInputEditText) {
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