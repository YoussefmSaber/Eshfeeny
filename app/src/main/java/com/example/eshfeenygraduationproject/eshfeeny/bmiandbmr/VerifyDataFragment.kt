package com.example.eshfeenygraduationproject.eshfeeny.bmiandbmr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentVerifyDataBinding

import com.google.android.material.bottomsheet.BottomSheetDialog


class VerifyDataFragment : Fragment() {
    private var binding: FragmentVerifyDataBinding? = null
    private val args:VerifyDataFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyDataBinding.inflate(layoutInflater)
        val age = args.age
        val height = args.height
        val weight = args.weight
        val gender = args.gender
        binding?.txtAgeId?.text = "$age age"
        binding?.txtCmId?.text = "$height cm"
        binding?.txtKgId?.text = "$weight kg"
        val height1 = height.toFloat()
        val weight1 = weight.toFloat()
        if(gender == "أنثى") {
            binding?.imgManId?.visibility = View.INVISIBLE
            binding?.imgWomanId?.visibility = View.VISIBLE
        }
        binding?.btnEdit?.setOnClickListener {
            val action =
                VerifyDataFragmentDirections.actionVerifyDataFragmentToBmiAndBmrFragment(age,height,weight,gender)
            findNavController().navigate(action)
        }
        binding?.btnCal?.setOnClickListener {
            val bmr = calcBMR(age?.toFloat(),height1,weight1,gender)
            val bmi = calcBMI(height1,weight1)
            val view: View = layoutInflater.inflate(R.layout.bottom_sheet_to_calculate_bmi_bmr, null)
            val dialog = BottomSheetDialog(requireContext())
            val BtnIdBmi = view.findViewById<Button>(R.id.btnIdCalcBmi)
            val BtnIdBmr = view.findViewById<Button>(R.id.btnIdCalcBmr)

            BtnIdBmi.setOnClickListener {
                dialog.dismiss()
                val action = VerifyDataFragmentDirections.actionVerifyDataFragmentToBmiResultFragment2(bmi, gender)
                findNavController().navigate(action)
            }
            val txtExit = view.findViewById<TextView>(R.id.txtIdExit)
            txtExit.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setContentView(view)
            dialog.show()
        }
        // Inflate the layout for this fragment
        return binding?.root
    }
    fun calcBMR(age: Float?, height: Float?, weight: Float?, gender: String?) : Double {
        return if(gender != "أنثى")
            88.362 + (13.397 * weight!!) + (4.799 * height!!) - (5.677 * age!!)
        else
            447.593  + (9.247 * weight!!) + (3.098 * height!!) - (4.330 * age!! )
    }
    fun calcBMI(height: Float?, weight: Float?) : Float {
        return (weight!!/((height!!/100)*(height /100)))
    }
}