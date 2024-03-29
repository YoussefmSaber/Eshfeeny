package com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.eshfeenygraduationproject.databinding.FragmentTimePickerBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.EditAlarmFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.SetAlarmFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class TimePickerFragment(private val alarmState: String) : BottomSheetDialogFragment() {

    // creating the binding variable and giving it' initial value to null
    private var binding: FragmentTimePickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // giving the binding variable it's value
        binding = FragmentTimePickerBinding.inflate(inflater)

        // making a click listener to the setTime button to take the values from the time picker
        binding?.setTime?.setOnClickListener {
            // taking the hour and minute values from the time picker in 24hour format
            val hour = binding?.timePicker?.hour
            val minute = binding?.timePicker?.minute

            // getting an instance of the calender to get the hour and minute
            val selectedTime = Calendar.getInstance()
            hour?.let {
                selectedTime.set(Calendar.HOUR_OF_DAY, it)
            }
            minute?.let {
                selectedTime.set(Calendar.MINUTE, it)
            }
            selectedTime.set(Calendar.SECOND, 0)
            selectedTime.set(Calendar.MILLISECOND, 0)

            // Format the selected time in the desired format (hour:minute am/pm)
            val timeFormat = SimpleDateFormat("h:mm a")
            val formattedTime = timeFormat.format(selectedTime.time)

            when(alarmState) {
                "set" -> {
                    val parentFragment = parentFragment as SetAlarmFragment
                    parentFragment.onTimeSelected(formattedTime, selectedTime.timeInMillis)
                    dismiss()
                }
                "edit" -> {
                    val parentFragment = parentFragment as EditAlarmFragment
                    parentFragment.onTimeSelected(formattedTime, selectedTime.timeInMillis)
                    dismiss()
                }
            }

        }
        return binding?.root
    }
}