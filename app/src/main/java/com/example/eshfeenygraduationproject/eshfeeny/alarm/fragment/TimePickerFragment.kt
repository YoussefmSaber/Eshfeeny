package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentTimePickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment : BottomSheetDialogFragment() {

    // creating the binding variable and giving it' initial value to null
    private var binding: FragmentTimePickerBinding ?= null

    @RequiresApi(Build.VERSION_CODES.M)
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
            if (hour != null)
                selectedTime.set(Calendar.HOUR_OF_DAY, hour)
            if (minute != null)
                selectedTime.set(Calendar.MINUTE, minute)
            // Format the selected time in the desired format (hour:minute am/pm)
            val timeFormat = SimpleDateFormat("h:mm a")
            val formattedTime = timeFormat.format(selectedTime.time)

            val parentFragment = parentFragment as SetAlarmFragment
            parentFragment.onTimeSelected(formattedTime)
            dismiss()
        }
        return binding?.root
    }
}