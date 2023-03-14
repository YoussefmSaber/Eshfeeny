package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSignupBinding
import com.google.android.material.textfield.*


class SignupFragment : Fragment() {
    private var binding: FragmentSignupBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)

        binding?.nameSignup?.let {
            setHint(it, R.string.userName)
        }
        binding?.emailSignup?.let {
            setHint(it, R.string.Email)
        }
        binding?.passwordSignup?.let {
            binding?.PasswordSignup?.let { it1 ->
                setHint(
                    it,
                    it1, R.string.pass
                )
            }
        }
        binding?.confirmPassSignup?.let {
            binding?.ConfirmPassSignup?.let { it1 ->
                setHint(
                    it,
                    it1, R.string.confPass
                )
            }
        }

        binding?.createInSignup?.setOnClickListener {
            //action from signup to login
            Navigation.findNavController(it).navigate(R.id.Signup2Login)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setHint(view: TextInputEditText, parent: TextInputLayout, string: Int) {

        view.hint = getString(string)

        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                parent.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                view.hint = ""
                (v as TextInputEditText).gravity = Gravity.LEFT
            } else {
                parent.endIconMode = TextInputLayout.END_ICON_NONE
                if (view.text?.isEmpty() == true) {
                    view.hint = getString(string)
                    (v as TextInputEditText).gravity = Gravity.RIGHT
                }
            }
        }
    }

    private fun setHint(view: TextInputEditText, string: Int) {

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