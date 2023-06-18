package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSelectDaysBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectDaysFragment : BottomSheetDialogFragment() {

    private var binding: FragmentSelectDaysBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectDaysBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}