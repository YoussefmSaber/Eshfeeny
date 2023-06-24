package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.repository.AlarmRepoImpl
import com.example.domain.entity.alarm.Alarm
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAlarmBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter.AlarmAdapter
import com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter.DaysAdapter
import com.example.eshfeenygraduationproject.eshfeeny.alarm.viewModel.AlarmViewModel
import com.example.eshfeenygraduationproject.eshfeeny.alarm.viewModel.AlarmViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.util.Days
import com.example.eshfeenygraduationproject.eshfeeny.util.DaysList
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class AlarmFragment : Fragment() {

    // Creating the binding variable and setting it default value to null
    var binding: FragmentAlarmBinding? = null
    private lateinit var userId: String
    private lateinit var viewModel: AlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initializing the binding variable to FragmentALarmBinding
        binding = FragmentAlarmBinding.inflate(inflater)
        initializeViewModels()
        // initializing the adapter variable and changing the value of the month and year
        // if it's value changed in the calendar
        var selectedDayInMilli = Long.valueOf(0)
        binding?.backBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_alarmFragment_to_moreFragment2)
        }


        val adapter = DaysAdapter(DaysList.daysList, { month, year ->
            if (month.isEmpty()) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                val monthDateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
                val monthName = monthDateFormat.format(calendar.time)
                val yearNumber = calendar.get(Calendar.YEAR)
                binding?.MonthTextView?.text = "${monthName} ,${yearNumber}"
            }
            binding?.MonthTextView?.text = "${month} ,${year}"
        }, { selectedDay ->
            selectedDayInMilli = selectedDay.dayInMilli
            Log.d("Alarm", selectedDayInMilli.toString())
            viewModel.getAlarm(userId)
            viewModel.alarms.observe(viewLifecycleOwner) { listAlarm ->
                val availableAlarms: MutableList<Alarm> = mutableListOf()
                if (listAlarm.isNotEmpty()) {
                    listAlarm.forEach { dayAlarms ->
                        val start = dayAlarms.startDate.toLong()
                        val end = dayAlarms.endDate.toLong()

                        if (selectedDayInMilli in start..end) {
                            binding?.alarmRecyclerView?.visibility = View.VISIBLE
                            binding?.noAlarms?.visibility = View.GONE
                            availableAlarms.add(dayAlarms)
                        } else {
                            binding?.alarmRecyclerView?.visibility = View.GONE
                            binding?.noAlarms?.visibility = View.VISIBLE
                        }
                    }
                    val alarmAdapter = AlarmAdapter()
                    binding?.alarmRecyclerView?.adapter = alarmAdapter
                    alarmAdapter.submitList(availableAlarms)
                }
            }
        })

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

    private fun initializeViewModels() {
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val alarmRepo = AlarmRepoImpl()
        val alarmViewModelFactory = AlarmViewModelFactory(alarmRepo)
        viewModel = ViewModelProvider(this, alarmViewModelFactory)[AlarmViewModel::class.java]
        getUserId(userViewModel)
    }

    private fun getUserId(userViewModel: UserViewModel) {
        userViewModel.userData.observe(viewLifecycleOwner) { userData ->
            userId = userData._id.toString()
        }
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
                        formatDayID.format(calendar.time),
                        calendar.timeInMillis
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