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