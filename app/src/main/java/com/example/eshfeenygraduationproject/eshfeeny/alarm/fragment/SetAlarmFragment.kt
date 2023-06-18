package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentSetAlarmBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet.AlarmDurationFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet.SelectDaysFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet.TimePickerFragment
import com.example.eshfeenygraduationproject.eshfeeny.util.AlarmReceiver
import com.example.eshfeenygraduationproject.eshfeeny.util.channelId
import com.example.eshfeenygraduationproject.eshfeeny.util.notificationId
import com.google.android.material.chip.Chip
import kotlin.random.Random

const val titleExtra = "medicName"
const val descExtra = "medicDesc"

class SetAlarmFragment : Fragment() {

    // Creating the binding variable to access the views in the SetAlarm Fragment
    // and setting it's intial value to null
    private var binding: FragmentSetAlarmBinding? = null
    private lateinit var repetitionState: String
    private var alarmTime: MutableList<Long> = mutableListOf()
    private var alarmDuration: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // initializing the value of the binding variable
        binding = FragmentSetAlarmBinding.inflate(inflater)
        repetitionState =
            getString(com.example.eshfeenygraduationproject.R.string.repetition_only_today)

        // variable to get he number of bills per time
        var repetitionNumber = 1
        createNotificationChannel()

        binding?.confButtonAlarm?.setOnClickListener {

            setAlarm()
        }

        // showing the bottom sheet to set the alarm when press on the chip
        binding?.newAlarmChip?.setOnClickListener {
            showTimePicker()
        }

        binding?.RepetitionStateText?.setOnClickListener {
            showSelectRepetition()
        }

        binding?.durationTextView?.setOnClickListener {
            showDurationSetter()
        }

        // increasing and decreasing the number of bills that will be taken in a time depending on
        // which button is pressed
        binding?.plusIcon?.setOnClickListener {
            repetitionNumber += 1
            binding?.repetitionNumber?.text = repetitionNumber.toString()
        }
        // in case of decreasing the minimum amount of bills is 1
        binding?.minusIcon?.setOnClickListener {
            if (repetitionNumber > 1) {
                repetitionNumber -= 1
                binding?.repetitionNumber?.text = repetitionNumber.toString()
            }
        }

        return binding?.root
    }

    private fun createNotificationChannel() {
        val name = "Alarm Channel"
        val desc = "This is a channel to show alarms for MedFinder Application"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = desc
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun setAlarm() {

        alarmTime.forEach {

            val intent = Intent(requireContext().applicationContext, AlarmReceiver::class.java).apply {
                putExtra(titleExtra, binding?.medcienNameInput?.text.toString())
                putExtra(descExtra, binding?.DescriptionInput?.text.toString())
            }

            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                it,
                PendingIntent.getBroadcast(
                    requireContext(),
                    it.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                )
            )
        }
    }

    private fun showDurationSetter() {
        val bottomSheet = AlarmDurationFragment()
        bottomSheet.show(childFragmentManager, "AlarmDurationFragment")
    }

    private fun showSelectRepetition() {
        val bottomSheet = SelectDaysFragment()
        bottomSheet.show(childFragmentManager, "SelectDaysFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        // setting the binding variable to null to ensure there is no memory leak
        binding = null
    }

    // a function to show the timePicker bottom sheet
    private fun showTimePicker() {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(childFragmentManager, "TimePickerFragment")
    }

    // a function that set that pass the time to the chip while creating it
    fun onTimeSelected(timeString: String, timeMillis: Long) {
        alarmTime.add(timeMillis)
        Log.d("alarm", alarmTime.toString())
        val newChip = createChip(timeString)
        // adding the chip to the chipGroup
        binding?.alarmChipsGroup?.addView(newChip)
    }

    // a function that create the chips when the time is selected
    private fun createChip(time: String): Chip {
        // creating a chip variable and setting the text of the chip to the time
        val chip = Chip(context)
        chip.text = time
        chip.chipCornerRadius = 50f
        // making the chip direction in Left to right mode
        chip.layoutDirection = View.LAYOUT_DIRECTION_LTR
        // setting the chip stroke width to 1dp
        chip.chipStrokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            context?.resources?.displayMetrics
        )
        // changing the chip background color
        chip.chipBackgroundColor =
            context?.let {
                ContextCompat.getColor(it, R.color.white)
            }?.let {
                ColorStateList.valueOf(it)
            }
        // changing the chip stroke color
        chip.chipStrokeColor =
            context?.let {
                ContextCompat.getColor(it, R.color.blue_main)
            }?.let {
                ColorStateList.valueOf(it)
            }
        // making the close icon visible
        chip.isCloseIconVisible = true
        // changing the close icon color
        chip.closeIconTint =
            context?.let {
                ContextCompat.getColor(it, R.color.blue_main)
            }?.let {
                ColorStateList.valueOf(it)
            }
        // setting a click listener on the close icon to remove the chip from the group
        chip.setOnCloseIconClickListener {
            binding?.alarmChipsGroup?.removeView(it)
        }
        return chip
    }

    fun alarmDuration(duration: Int) {
        alarmDuration = duration
        when (alarmDuration) {
            0 -> {
                binding?.durationTextView?.text = "اختار المدة"
            }

            else -> {
                binding?.durationTextView?.text = "$alarmDuration يوم"
            }
        }
    }

    fun alarmRepetition(reputationState: String) {
        binding?.RepetitionStateText?.text = reputationState
        repetitionState = reputationState

        when (reputationState) {
            getString(R.string.repetition_only_today) -> {
                alarmDuration = 0
                binding?.textView5?.visibility = View.GONE
                binding?.durationLayout?.visibility = View.GONE
            }

            else -> {
                binding?.textView5?.visibility = View.VISIBLE
                binding?.durationLayout?.visibility = View.VISIBLE
            }
        }
    }
}