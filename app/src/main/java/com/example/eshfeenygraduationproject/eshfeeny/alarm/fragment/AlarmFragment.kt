package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAlarmBinding
import com.example.eshfeenygraduationproject.eshfeeny.util.*
import com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter.DaysAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlarmFragment : Fragment() {

    // Creating the binding variable and setting it default value to null
    var binding: FragmentAlarmBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initializing the binding variable to FragmentALarmBinding
        binding = FragmentAlarmBinding.inflate(inflater)
        // initializing the adapter variable and changing the value of the month and year
        // if it's value changed in the calendar
        val adapter = DaysAdapter(DaysList.daysList) { month, year ->
            binding?.MonthTextView?.text = "${month} ,${year}"
        }
        // initializing the adapter of the recycler view
        binding?.calendarDaySelectRV?.adapter = adapter
        // adding a navigation to the add button to go to Set alarm page
        binding?.addAlarmBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_alarmFragment_to_setAlarmFragment)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        // setting the binding to null to prevent any memory leak
        binding = null
    }

    companion object {
        // a function to create the day and get the next days
        fun getNextDays(days: Int): List<Days> {
            // getting an instance of the calender
            val calendar = Calendar.getInstance()
            // creating a format for the dayID cause every day is different from the other
            val formatDayID = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            // getting the day number
            val formatDayNumber = SimpleDateFormat("dd", Locale.getDefault())
            // getting the day name
            val formatDay = SimpleDateFormat("EEEE", Locale("ar"))
            // getting the month name
            val formatMonth = SimpleDateFormat("MMMM", Locale("ar"))
            // getting the year
            val formatYear = SimpleDateFormat("yyyy", Locale.getDefault())
            // creating an array list to hold the days
            val nextDays = ArrayList<Days>()
            // a loop to iterate over the number of days specified
            for (i in 1..days) {
                nextDays.add(
                    // using the data class Days to set the value of the day
                    Days(
                        formatDayNumber.format(calendar.time),
                        formatDay.format(calendar.time),
                        formatMonth.format(calendar.time),
                        formatYear.format(calendar.time),
                        formatDayID.format(calendar.time)
                    )
                )
                // making the calender goes to the next day
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            // returning the days variable
            return nextDays
        }
    }
}