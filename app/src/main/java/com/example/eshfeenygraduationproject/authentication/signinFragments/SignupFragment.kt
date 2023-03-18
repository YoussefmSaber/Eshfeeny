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
import com.example.domain.entity.CreateUser
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModelFactory
import com.example.eshfeenygraduationproject.authentication.viewmodels.SharedViewModel
import com.example.eshfeenygraduationproject.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var binding: FragmentSignupBinding? = null
    private lateinit var viewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater)

        val repository = UserRepoImpl()
        val viewModelFactory = SharedViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        binding?.confirmButtonSignup?.setOnClickListener {
            val password = binding!!.passwordSignup.text.toString()
            val confPassword = binding!!.confirmPassSignup.text.toString()
            val email = binding!!.emailSignup.text.toString()
            val name = binding!!.nameSignup.text.toString()

            if (password != confPassword) {
                binding!!.PasswordSignup.error = getString(R.string.passwordNotMatch)
                binding!!.ConfirmPassSignup.error = getString(R.string.passwordNotMatch)
            } else {
                val newUser = CreateUser(
                    email, name, password
                )
                viewModel.createNewUser(newUser)
                viewModel.createUserResponse.observe(viewLifecycleOwner, Observer {

                    if (it.errorBody()?.string()!! == "Email Already Exists") {
                        binding!!.emailSignupLayout.error = getString(R.string.emailAlreadyUsed)
                        Log.i("response", viewModel.emailAlreadyExists + "Fragment")
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Your account is created",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
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
}