package com.example.eshfeenygraduationproject.eshfeeny.Util

import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.AlarmFragment

data class Days(
    val dayNumber: String,
    val dayName: String,
    val monthName: String,
    val yearNumber: String,
    val dayID: String
)

object DaysList {
    val daysList = AlarmFragment.getNextDays(2000)
}
