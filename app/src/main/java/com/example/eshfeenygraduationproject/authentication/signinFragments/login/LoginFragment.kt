package com.example.eshfeenygraduationproject.authentication.signinFragments.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.domain.entity.VerifyLoginResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentLoginBinding
import com.example.eshfeenygraduationproject.eshfeeny.MainActivity

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        binding?.confirmButtonSignin?.setOnClickListener {
            val userData = VerifyLoginResponse(
                binding?.nameSignin?.text.toString(),
                binding?.passwordSignup?.text.toString()
            )

            viewModel.verifyLogin(userData)
            viewModel.verifyUserLogin.observe(viewLifecycleOwner) {
                Log.i("login", "${it.body()} + ${it.code()}")
                if (it.body() != null) {
                    Log.i("login", it.body().toString())
                    Toast.makeText(
                        requireContext(),
                        "You have been logged in successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    it.body()?.let { userInfo ->
                        Log.i("login", userInfo.toString())
                        viewModel.addUserToDatabase(userInfo)
                        Log.i("DB", userInfo.toString())
                        val intent = Intent(
                            activity,
                            MainActivity::class.java
                        )
                        Log.i("Login", intent.toString())
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(requireContext(), "Check your data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding?.forgetPasswordEditText?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_sendCodeToMailFragment)
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
}