package com.example.eshfeenygraduationproject.eshfeeny.bmiandbmr

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.databinding.FragmentBmiResultBinding
import kotlin.math.roundToInt


class BmiResultFragment : Fragment() {
    private lateinit var binding : FragmentBmiResultBinding
    val args: BmiResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBmiResultBinding.inflate(layoutInflater)
        val bmi1 = args.bmi
        val bmi = bmi1.roundToInt()
        val gender = args.gender
        binding.lineBId.visibility = View.INVISIBLE
        binding.lineGId.visibility = View.INVISIBLE
        binding.lineOId.visibility = View.INVISIBLE
        binding.lineRId.visibility = View.INVISIBLE
        if(gender == "أنثى"){
            binding.womanResIdBmi.visibility = View.VISIBLE
            binding.manResIdBmi.visibility = View.INVISIBLE
        }
        if(bmi < 18.5){
            binding.txtResIdBmi.text = "نقص الوزن$bmi "
            binding.txtResIdBmi.setTextColor(Color.parseColor("#1976D2"))
            binding.lineBId.visibility = View.VISIBLE
            binding.lineBId.translationX = (bmi * 11).toFloat()
        }
        else if (bmi >= 18.5 && bmi < 25){
            binding.txtResIdBmi.text = "وزن طبيعى$bmi "
            binding.txtResIdBmi.setTextColor(Color.parseColor("#388E3C"))
            binding.lineGId.visibility = View.VISIBLE
            binding.lineGId.translationX = (bmi * 2.5).toFloat()
        }
        else if (bmi in 25..29){
            binding.txtResIdBmi.text = "وزن زائد$bmi "
            binding.txtResIdBmi.setTextColor(Color.parseColor("#FF8000"))
            binding.lineOId.visibility = View.VISIBLE
            binding.lineOId.translationX = (bmi -20).toFloat()
        }
        else{
            binding.txtResIdBmi.text = "سمنة زائدة$bmi "
            binding.txtResIdBmi.setTextColor(Color.parseColor("#FF5252"))
            binding.lineRId.visibility = View.VISIBLE
            if(bmi >=40)
                binding.lineRId.translationX = (40*6 + 15).toFloat()
            else
                binding.lineRId.translationX = (bmi *3).toFloat()
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}