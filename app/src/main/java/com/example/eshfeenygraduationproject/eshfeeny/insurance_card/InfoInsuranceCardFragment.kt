package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentInfoInsuranceCardBinding


class InfoInsuranceCardFragment : Fragment() {
    private var binding:FragmentInfoInsuranceCardBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoInsuranceCardBinding.inflate(layoutInflater)
        binding?.CardNumEditText?.inputType = InputType.TYPE_CLASS_NUMBER
        binding?.backBtn22?.setOnClickListener {
            findNavController().navigate(R.id.action_infoInsuranceCardFragment_to_cartExistsFragment)
        }
        binding?.addCartExistsBtn?.setOnClickListener {
            if(binding?.CardNameEditText?.text?.isEmpty()==true || binding?.CardNumEditText?.text?.isEmpty()==true)
            {
                Toast.makeText(context,"اكمل البيانات",Toast.LENGTH_SHORT).show()

            }
            else{
                findNavController().navigate(R.id.action_infoInsuranceCardFragment_to_addPhotoICFragment)
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }
}