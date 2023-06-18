package com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSelectDaysBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.SetAlarmFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectDaysFragment : BottomSheetDialogFragment() {

    private var binding: FragmentSelectDaysBinding? = null
    private var selectedRepetition = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectDaysBinding.inflate(inflater)

        binding?.onlyToday?.setOnClickListener { onRepetitionSelected(getString(R.string.repetition_only_today)) }
        binding?.everyDay?.setOnClickListener { onRepetitionSelected(getString(R.string.repetition_every_day)) }
        binding?.dayAndDay?.setOnClickListener { onRepetitionSelected(getString(R.string.repetition_day_and_day)) }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private fun onRepetitionSelected(repetition: String) {
        selectedRepetition = repetition
        val parentFragment = parentFragment as SetAlarmFragment
        parentFragment.alarmRepetition(selectedRepetition)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}