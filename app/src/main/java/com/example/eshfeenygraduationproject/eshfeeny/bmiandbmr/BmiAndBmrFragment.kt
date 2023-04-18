package com.example.eshfeenygraduationproject.eshfeeny.bmiandbmr

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentBmiAndBmrBinding
import com.google.android.material.textfield.TextInputEditText


class BmiAndBmrFragment : Fragment() {
    private var binding:FragmentBmiAndBmrBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBmiAndBmrBinding.inflate(layoutInflater)
        forBindingId()
        forLetId()
        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_bmiAndBmrFragment_to_moreFragment2)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi_and_bmr, container, false)
    }
    private fun setHint(view: TextInputEditText, string: Int) {
        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                view.hint = ""
                (v as TextInputEditText).gravity = Gravity.RIGHT
            } else {
                if (view.text?.isEmpty() == true) {
                    view.hint = getString(string)
                    (v as TextInputEditText).gravity = Gravity.RIGHT
                }
            }
        }
    }
    private fun forBindingId(){
        binding?.ageEditText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.heightEditText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.weightEditText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.ageEditText?.hint = getString(R.string.YourAge)
        binding?.heightEditText?.hint = getString(R.string.CM)
        binding?.weightEditText?.hint = getString(R.string.KM)
    }
    private fun forLetId(){
        binding?.ageEditText?.let {
            setHint(it, R.string.YourAge)
        }
        binding?.heightEditText?.let {
            setHint(it, R.string.CM)
        }
        binding?.weightEditText?.let {
            setHint(it, R.string.KM)
        }
    }
}