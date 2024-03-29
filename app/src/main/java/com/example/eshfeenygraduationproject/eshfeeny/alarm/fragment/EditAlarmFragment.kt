package com.example.eshfeenygraduationproject.eshfeeny.alarm.fragment

import android.app.AlarmManager
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.repository.AlarmRepoImpl
import com.example.domain.entity.alarm.AlarmPatchRequest
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentEditAlarmBinding
import com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet.AlarmDurationFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet.SelectDaysFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.bottomsheet.TimePickerFragment
import com.example.eshfeenygraduationproject.eshfeeny.alarm.viewModel.AlarmViewModel
import com.example.eshfeenygraduationproject.eshfeeny.alarm.viewModel.AlarmViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.viewModel.UserViewModel
import com.example.eshfeenygraduationproject.eshfeeny.util.AlarmReceiver
import com.example.eshfeenygraduationproject.eshfeeny.util.channelId
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class EditAlarmFragment : Fragment() {

    private val args: EditAlarmFragmentArgs by navArgs()
    private var binding: FragmentEditAlarmBinding? = null
    private var alarmTime: MutableList<Long> = mutableListOf()
    private var alarmDuration = 0
    private var dose = 1
    private var loadingDialog: Dialog? = null
    private lateinit var repetitionState: String
    private lateinit var userId: String
    private lateinit var viewModel: AlarmViewModel
    private lateinit var name: String
    private lateinit var desc: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditAlarmBinding.inflate(inflater)

        initializeViewModels()
        settingAlarmValues()
        createNotificationChannel()
        buttonsClickListeners()
        setDataToUI()
        deleteAlarm()

        return binding?.root
    }

    private fun deleteAlarm() {
        binding?.deleteAlarmButton?.setOnClickListener {
            viewModel.deleteAlarm(userId, args.alarm._id)
            findNavController().navigate(R.id.action_editAlarmFragment_to_alarmFragment)
        }
    }

    private fun setDataToUI() {
        binding?.apply {
            this.medcienNameInput.setText(args.alarm.name)
            this.DescriptionInput.setText(args.alarm.notes)
            this.durationTextView.text = args.alarm.dose.toString()
            args.alarm.alarmTime.forEach {
                val pattern = "hh:mm a" // Desired time format
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                val formattedTime = sdf.format(Date(it))
                onTimeSelected(formattedTime, it)
            }
        }
    }

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(requireContext())
            loadingDialog!!.setContentView(R.layout.alarm_warning)
            loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog!!.setCancelable(true)
        }
        loadingDialog!!.show()
    }

    private fun buttonsClickListeners() {
        binding?.confButtonAlarm?.setOnClickListener {
            if (elementNotFilled()){
                showLoadingDialog()
            } else {
                setAlarm()
            }
        }

        binding?.BackArrow?.setOnClickListener {
            findNavController().navigate(R.id.action_editAlarmFragment_to_alarmFragment)
        }

        binding?.newAlarmChip?.setOnClickListener {
            showTimePicker()
        }

        binding?.RepetitionStateText?.setOnClickListener {
            showSelectRepetition()
        }

        binding?.durationTextView?.setOnClickListener {
            showDurationSetter()
        }

        binding?.plusIcon?.setOnClickListener {
            dose += 1
            binding?.repetitionNumber?.text = dose.toString()
        }

        binding?.minusIcon?.setOnClickListener {
            if (dose > 1) {
                dose -= 1
                binding?.repetitionNumber?.text = dose.toString()
            }
        }
    }

    private fun elementNotFilled(): Boolean {
        val nameState = binding?.medcienNameInput?.text?.length == 0
        val noteState = binding?.DescriptionInput?.text?.length == 0
        val alarmState = binding?.alarmChipsGroup?.childCount == 1

        if (repetitionState != getString(R.string.repetition_only_today)) {
            val durationState = alarmDuration == 0
            return nameState || noteState || alarmState || durationState
        }

        return nameState || noteState || alarmState
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val alarmName = binding?.medcienNameInput?.text.toString()
        val alarmNote = binding?.DescriptionInput?.text.toString()
        val intent = Intent(requireContext().applicationContext, AlarmReceiver::class.java).apply {
            putExtra(titleExtra, alarmName)
            putExtra(descExtra, alarmNote)
        }

        val startDate = calendar.timeInMillis
        var endDate = ""

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        when (repetitionState) {
            getString(R.string.repetition_only_today) -> {
                endDate = (startDate + TimeUnit.DAYS.toMillis(1)).toString()
                alarmTime.forEach {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        it,
                        PendingIntent.getBroadcast(
                            requireContext(),
                            it.hashCode(),
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                }
                viewModel.editAlarm(
                    userId,
                    args.alarm._id,
                    AlarmPatchRequest(
                        alarmName,
                        alarmNote,
                        binding?.repetitionNumber?.text.toString().toInt(),
                        repetitionState,
                        alarmTime,
                        startDate.toString(),
                        endDate
                    )
                )
            }

            getString(R.string.repetition_every_day) -> {

                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val endData =
                    System.currentTimeMillis() + TimeUnit.DAYS.toMillis((alarmDuration - 1).toLong())
                endDate = endData.toString()
                alarmTime.forEach {

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        it,
                        calendar.timeInMillis,
                        PendingIntent.getBroadcast(
                            requireContext(),
                            it.hashCode(),
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )

                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        endData,
                        PendingIntent.getBroadcast(
                            requireContext(),
                            it.hashCode(),
                            Intent(requireContext().applicationContext, AlarmReceiver::class.java),
                            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                }

                viewModel.editAlarm(
                    userId,
                    args.alarm._id,
                    AlarmPatchRequest(
                        alarmName,
                        alarmNote,
                        binding?.repetitionNumber?.text.toString().toInt(),
                        repetitionState,
                        alarmTime,
                        startDate.toString(),
                        endDate
                    )
                )
            }

            getString(R.string.repetition_day_and_day) -> {
                val intervalDays = 2
                val alarmDateList = mutableListOf<String>()
                alarmDateList.add(calendar.timeInMillis.toString())
                calendar.add(Calendar.DAY_OF_MONTH, intervalDays)

                val endData =
                    System.currentTimeMillis() + TimeUnit.DAYS.toMillis(((alarmDuration - 1) * 2).toLong())
                endDate = endData.toString()

                val alarmTimeList = mutableListOf<Long>()

                // Generate alarm dates
                val currentDateTime = calendar.timeInMillis

                var alarmDateTime = currentDateTime
                while (alarmDateTime <= endData) {
                    alarmDateList.add(alarmDateTime.toString())
                    alarmDateTime += TimeUnit.DAYS.toMillis(intervalDays.toLong())
                }

                // Generate alarm times
                alarmDateList.forEach { date ->
                    alarmTime.forEach { time ->
                        val dateTime = date.toLong() + time
                        alarmTimeList.add(dateTime)
                    }
                }

                alarmTimeList.forEach {
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        it,
                        TimeUnit.DAYS.toMillis(intervalDays.toLong()),
                        PendingIntent.getBroadcast(
                            requireContext(),
                            it.hashCode(),
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )

                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        endData,
                        PendingIntent.getBroadcast(
                            requireContext(),
                            it.hashCode(),
                            Intent(requireContext().applicationContext, AlarmReceiver::class.java),
                            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                }

                viewModel.editAlarm(
                    userId,
                    args.alarm._id,
                    AlarmPatchRequest(
                        alarmName,
                        alarmNote,
                        binding?.repetitionNumber?.text.toString().toInt(),
                        repetitionState,
                        alarmTime,
                        startDate.toString(),
                        endDate,
                        alarmDateList
                    )
                )
            }
        }
        findNavController().navigate(R.id.action_editAlarmFragment_to_alarmFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun createNotificationChannel() {
        val name = "Alarm Channel"
        val desc = "This is a channel to show alarms for MedFinder Application"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = desc
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun settingAlarmValues() {
        repetitionState = args.alarm.repetition
        name = args.alarm.name
        desc = args.alarm.notes.toString()
        dose = args.alarm.dose
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

    private fun showDurationSetter() {
        val bottomSheet = AlarmDurationFragment("edit")
        bottomSheet.show(childFragmentManager, "AlarmDurationFragment")
    }

    private fun showSelectRepetition() {
        val bottomSheet = SelectDaysFragment("edit")
        bottomSheet.show(childFragmentManager, "SelectDaysFragment")
    }

    private fun showTimePicker() {
        val timePickerFragment = TimePickerFragment("edit")
        timePickerFragment.show(childFragmentManager, "TimePickerFragment")
    }

    fun onTimeSelected(timeString: String, timeMillis: Long) {
        alarmTime.add(timeMillis)
        val newChip = createChip(timeString, alarmTime, timeMillis)
        // adding the chip to the chipGroup
        binding?.alarmChipsGroup?.addView(newChip)
    }

    private fun createChip(time: String, alarmTime: MutableList<Long>, timeMillis: Long): Chip {
        // creating a chip variable and setting the text of the chip to the time
        val chip = Chip(context)
        chip.text = time
        chip.chipCornerRadius = 50F
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
            alarmTime.remove(timeMillis)
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