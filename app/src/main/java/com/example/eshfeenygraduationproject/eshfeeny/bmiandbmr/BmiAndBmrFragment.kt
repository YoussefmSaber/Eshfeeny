package com.example.eshfeenygraduationproject.eshfeeny.bmiandbmr

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentBmiAndBmrBinding
import com.google.android.material.textfield.TextInputEditText

@Suppress("DEPRECATION")
class BmiAndBmrFragment : Fragment() {
    private var binding:FragmentBmiAndBmrBinding? = null
    var flagAge = false
    var flagHeight = false
    var flagWeight = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBmiAndBmrBinding.inflate(layoutInflater)
        forBindingId()
        forLetId()
        val gender = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_items,gender)
        binding?.genderAutoComplete?.setAdapter(arrayAdapter)

        binding?.NextBtntInBMI?.setOnClickListener {
            bindingIsEmpty()
            val age = binding?.ageEditText?.text.toString()
            val height = binding?.heightEditText?.text.toString()
            val weight = binding?.weightEditText?.text.toString()
            val gender1 = binding?.genderAutoComplete?.text.toString()
            if (flagAge and flagHeight and flagWeight){
                findNavController().navigate(R.id.action_bmiAndBmrFragment_to_verifyDataFragment)

            }
        }
        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_bmiAndBmrFragment_to_moreFragment2)
        }
        // Inflate the layout for this fragment
        return binding?.root
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
    private fun bindingIsEmpty(){
        if(binding?.ageEditText?.text?.isEmpty() == true) {
            binding?.Star1?.visibility = View.VISIBLE
            binding?.Star1?.setTextColor(Color.parseColor("#EB1D36"))
            flagAge = false
        }
        else if (binding?.ageEditText?.text?.isEmpty() != true){
            binding?.Star1?.visibility = View.INVISIBLE
            binding?.AgeInputLayout?.boxStrokeColor = resources.getColor(R.color.light_blue1)
            flagAge = true
        }
        if(binding?.heightEditText?.text?.isEmpty() == true) {
            binding?.Star2?.visibility = View.VISIBLE
            binding?.Star2?.setTextColor(Color.parseColor("#EB1D36"))
            flagHeight = false
        }
        else if (binding?.heightEditText?.text?.isEmpty() != true){
            binding?.Star2?.visibility = View.INVISIBLE
            binding?.HeightInputLayout?.boxStrokeColor = resources.getColor(R.color.light_blue1)
            flagHeight = true

        }
        if(binding?.weightEditText?.text?.isEmpty() == true) {
            binding?.Star3?.visibility = View.VISIBLE
            binding?.Star3?.setTextColor(Color.parseColor("#EB1D36"))
            flagWeight = false
        }
        else if (binding?.weightEditText?.text?.isEmpty() != true){
            binding?.Star3?.visibility = View.INVISIBLE
            binding?.WeightInputLayout?.boxStrokeColor = resources.getColor(R.color.light_blue1)
            flagWeight = true
        }
        binding?.GenderInputLayout?.boxStrokeColor = resources.getColor(R.color.light_blue1)
    }
}