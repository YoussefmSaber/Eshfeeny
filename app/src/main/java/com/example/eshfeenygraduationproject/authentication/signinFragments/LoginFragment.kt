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
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModelFactory
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        val repository = UserRepoImpl()
        val viewModelFactory = SharedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        binding?.confirmButtonSignin?.setOnClickListener {
            val userData = VerifyLoginResponse(
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
}