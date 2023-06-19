package com.example.eshfeenygraduationproject.eshfeeny.insurance_card

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentInfoInsuranceCardBinding


class InfoInsuranceCardFragment : Fragment() {
    private var binding:FragmentInfoInsuranceCardBinding? = null
    private val args: InfoInsuranceCardFragmentArgs by navArgs()
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
                val cardName = binding?.CardNameEditText?.text.toString()
                val cardNumber = binding?.CardNumEditText?.text.toString()
                Log.d("Insurance Card", "$cardName $cardNumber")
                val action =
                    InfoInsuranceCardFragmentDirections
                        .actionInfoInsuranceCardFragmentToAddPhotoICFragment(cardName,cardNumber, args.insuranceCardName)
                findNavController().navigate(action)
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }
}