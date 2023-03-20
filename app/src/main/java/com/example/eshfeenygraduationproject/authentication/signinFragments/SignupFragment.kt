package com.example.eshfeenygraduationproject.authentication.signinFragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.data.repository.UserRepoImpl
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

        binding?.confirmButtonSignup?.setOnClickListener { button ->
            val password = binding?.passwordSignup?.text.toString()
            val confPassword = binding?.confirmPassSignup?.text.toString()
            val email = binding?.emailSignup?.text.toString()
            val name = binding?.nameSignup?.text.toString()

            if (password != confPassword) {

                binding?.passwordSignup?.error = getString(R.string.passwordNotMatch)
                binding?.confirmPassSignup?.error = getString(R.string.passwordNotMatch)

            } else {
                viewModel.checkEmailExist(email)
                viewModel.emailFound.observe(viewLifecycleOwner, Observer { emailFound ->
                    if (emailFound.body() != null) {
                        binding?.emailSignupLayout?.error = getString(R.string.emailAlreadyUsed)
                    } else {
                        val bundle = Bundle()

                        bundle.putString("newUserName", name)
                        bundle.putString("newUserEmail", email)
                        bundle.putString("newUserPassword", password)

                        val verifyFragment = VerifyFragment()
                        verifyFragment.arguments = bundle

                        viewModel.verifyNewUser(email)
                        Navigation.findNavController(button).navigate(R.id.action_signupFragment_to_verifyFragment)
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