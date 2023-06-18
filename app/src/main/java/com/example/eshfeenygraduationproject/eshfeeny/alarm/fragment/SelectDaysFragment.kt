package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.databinding.FragmentSelectDaysBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectDaysFragment : BottomSheetDialogFragment() {

    private var binding: FragmentSelectDaysBinding? = null
    private var selectedRepetition = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectDaysBinding.inflate(inflater)

        binding?.onlyToday?.setOnClickListener {
            selectedRepetition = "اليوم الحالي فقط"
            val parentFragment = parentFragment as SetAlarmFragment
            parentFragment.alarmRepetition(selectedRepetition)
            dismiss()
        }
        binding?.everyDay?.setOnClickListener {
            selectedRepetition = "كل يوم"
            val parentFragment = parentFragment as SetAlarmFragment
            parentFragment.alarmRepetition(selectedRepetition)
            dismiss()
        }
        binding?.dayAndDay?.setOnClickListener {
            selectedRepetition = "يوم و يوم"
            val parentFragment = parentFragment as SetAlarmFragment
            parentFragment.alarmRepetition(selectedRepetition)
            dismiss()
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}