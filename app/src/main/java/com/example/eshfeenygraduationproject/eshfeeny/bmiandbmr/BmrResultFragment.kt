package com.example.eshfeenygraduationproject.eshfeeny.bmiandbmr

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentBmrResultBinding
import kotlin.math.roundToInt


class BmrResultFragment : Fragment() {
    private lateinit var binding:FragmentBmrResultBinding
    val args:BmrResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentBmrResultBinding.inflate(layoutInflater)
        val bmr = args.bmr
        val bmr1 = bmr.roundToInt()
        val gender = args.gender
        binding.lineBId.visibility = View.INVISIBLE
        binding.lineGId.visibility = View.INVISIBLE
        binding.lineOId.visibility = View.INVISIBLE
        binding.lineRId.visibility = View.INVISIBLE
        binding.linePId.visibility = View.INVISIBLE
        if(gender == "أنثى"){
            binding.womanResIdBmr.visibility = View.VISIBLE
            binding.manResIdBmr.visibility = View.INVISIBLE
        }
        if(bmr < 1500){
            binding.txtResIdBmr.text = "للثبات علي نفس الوزن يتم استهلاك Calories/Day $bmr"
            binding.txtResIdBmr.setTextColor(Color.parseColor("#1976D2"))
            binding.lineBId.visibility = View.VISIBLE
            binding.lineBId.translationX = (bmr * (50/1000))
        }
        else if (bmr >= 1500 && bmr < 2000){
            binding.txtResIdBmr.text = "للثبات علي نفس الوزن يتم استهلاك Calories/Day $bmr"
            binding.txtResIdBmr.setTextColor(Color.parseColor("#388E3C"))
            binding.lineGId.visibility = View.VISIBLE
            binding.lineGId.translationX = (bmr * (50/1500))
        }
        else if (bmr >= 2000 && bmr < 2500){
            binding.txtResIdBmr.text = "للثبات علي نفس الوزن يتم استهلاك Calories/Day $bmr"
            binding.txtResIdBmr.setTextColor(Color.parseColor("#FF8000"))
            binding.lineOId.visibility = View.VISIBLE
            binding.lineOId.translationX = (bmr * (50/2000))
        }
        else if (bmr >= 2500 && bmr < 3000) {
            binding.txtResIdBmr.text = "للثبات علي نفس الوزن يتم استهلاك Calories/Day $bmr"
            binding.txtResIdBmr.setTextColor(Color.parseColor("#FF5252"))
            binding.lineRId.visibility = View.VISIBLE
            binding.lineRId.translationX = (bmr * (50/2500)).toFloat()
        }
        else {
            binding.txtResIdBmr.text = "للثبات علي نفس الوزن يتم استهلاك Calories/Day $bmr"
            binding.txtResIdBmr.setTextColor(Color.parseColor("#5D3891"))
            binding.linePId.visibility = View.VISIBLE
            if(bmr >=3500)
                binding.linePId.translationX = (bmr * (50/3500)).toFloat()
            else
                binding.linePId.translationX = (bmr * (50/3000)).toFloat()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}