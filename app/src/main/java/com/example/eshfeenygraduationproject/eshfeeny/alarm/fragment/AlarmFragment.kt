package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentAlarmBinding
import com.example.eshfeenygraduationproject.eshfeeny.Util.Days
import com.example.eshfeenygraduationproject.eshfeeny.Util.DaysList
import com.example.eshfeenygraduationproject.eshfeeny.alarm.adapter.DaysAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AlarmFragment : Fragment() {

    lateinit var binding: FragmentAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(inflater)

        val adapter = DaysAdapter(DaysList.daysList){ month, year ->

            binding.MonthTextView.text = "${month} ,${year}"
        }
        Log.i("AlarmFragment", adapter.currentMonthName)
        binding.calendarDaySelectRV.adapter = adapter

        return binding.root
    }

    companion object {
        fun getNextDays(days: Int): List<Days> {
            val calendar = Calendar.getInstance()
            val formatDayID = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatDayNumber = SimpleDateFormat("dd", Locale.getDefault())
            val formatDay = SimpleDateFormat("EEEE", Locale("ar"))
            val formatMonth = SimpleDateFormat("MMMM", Locale("ar"))
            val formatYear = SimpleDateFormat("yyyy", Locale.getDefault())
            val nextDays = ArrayList<Days>()
            for (i in 1..days) {
                nextDays.add(
                    Days(
                        formatDayNumber.format(calendar.time),
                        formatDay.format(calendar.time),
                        formatMonth.format(calendar.time),
                        formatYear.format(calendar.time),
                        formatDayID.format(calendar.time)
                    )
                )
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return nextDays
        }
    }
}