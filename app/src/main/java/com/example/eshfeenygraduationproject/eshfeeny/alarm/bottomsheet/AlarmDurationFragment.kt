package com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAlarmDurationBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.SetAlarmFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AlarmDurationFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAlarmDurationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlarmDurationBinding.inflate(inflater)

        binding?.apply {
            this.durationConfButton.setOnClickListener {
                val parentFragment = parentFragment as SetAlarmFragment
                parentFragment.alarmDuration(this.durationInputText.text.toString().toInt())
                dismiss()
            }
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}