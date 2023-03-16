package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.data.repository.UserRepoImpl
import com.example.domain.entity.VerifyLoginResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.LoginViewModel
import com.example.eshfeenygraduationproject.authentication.viewmodels.LoginViewModelFactory
import com.example.eshfeenygraduationproject.databinding.FragmentLoginBinding
import com.google.android.material.textfield.*


class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        binding?.passwordSignup?.hint = getString(R.string.pass)
        binding?.nameSignin?.hint = getString(R.string.Email)

        val repository = UserRepoImpl()
        val viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding?.nameSignin?.let {
            setHint(it)
        }
        binding?.passwordSignup?.let {
            binding?.PasswordSignup?.let { it1 ->
                setHint(it, it1)
            }
        }

        binding?.confirmButtonSignin?.setOnClickListener {
            var userData: VerifyLoginResponse = VerifyLoginResponse(
                binding?.nameSignin?.text.toString(),
                binding?.passwordSignup?.text.toString()
            )
            
            viewModel.verifyLogin(userData)
            viewModel.verifyUserLogin.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Log.i("category", it.body().toString())
                    Toast.makeText(requireContext(), "You have been logged in successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_SHORT).show()
                }
            })
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