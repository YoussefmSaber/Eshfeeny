package com.example.eshfeenygraduationproject.eshfeeny.util

import com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment.AlarmFragment

object DaysList {
    // Creating a list of the next 1999 days starting from today
    val daysList = AlarmFragment.getNextDays(2000)
}