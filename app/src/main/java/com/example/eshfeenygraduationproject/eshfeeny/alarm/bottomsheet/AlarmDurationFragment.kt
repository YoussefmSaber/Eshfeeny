package com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAlarmDurationBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.EditAlarmFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.SetAlarmFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AlarmDurationFragment(private val alarmState: String) : BottomSheetDialogFragment() {

    private var binding: FragmentAlarmDurationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlarmDurationBinding.inflate(inflater)

        binding?.apply {
            this.durationConfButton.setOnClickListener {
                when (alarmState) {
                    "set" -> {
                        if (this.durationInputText.length() == 0) {
                            this.durationInputText.error = "الرجاء ادخال مدة"
                        } else {
                            val parentFragment = parentFragment as SetAlarmFragment
                            parentFragment.alarmDuration(
                                this.durationInputText.text.toString().toInt()
                            )
                            dismiss()
                        }
                    }

                    "edit" -> {
                        if (this.durationInputText.length() == 0) {
                            this.durationInputText.error = "الرجاء ادخال مدة"
                        } else {
                            val parentFragment = parentFragment as EditAlarmFragment
                            parentFragment.alarmDuration(
                                this.durationInputText.text.toString().toInt()
                            )
                            dismiss()
                        }
                    }
                }

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