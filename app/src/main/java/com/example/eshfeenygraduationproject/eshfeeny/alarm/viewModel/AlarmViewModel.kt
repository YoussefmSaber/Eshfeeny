package com.example.eshfeenygraduationproject.eshfeeny.alarm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AlarmRepoImpl
import com.example.domain.entity.alarm.Alarm
import com.example.domain.entity.alarm.AlarmPatchRequest
import kotlinx.coroutines.launch

class AlarmViewModel(private val repo: AlarmRepoImpl) : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>>
        get() = _alarms
    fun getAlarm(userId: String) {
        viewModelScope.launch {
            _alarms.value = repo.getAlarms(userId)
        }
    }

    fun sendAlarm(
        userId: String,
        alarmItem: AlarmPatchRequest
    ) {
        viewModelScope.launch {
            try {
                repo.sendAlarm(userId, alarmItem)
            } catch (e: Exception) {
                Log.e("Alarm", "Error sending Alarm $e")
            }
        }
    }

    fun editAlarm(
        userId: String,
        alarmId: String,
        alarmItem: AlarmPatchRequest
    ) {
        viewModelScope.launch {
            try {
                repo.editAlarm(userId, alarmId, alarmItem)
            } catch (e: Exception) {
                Log.e("Alarm", "Error editing Alarm $e")
            }
        }
    }

    fun deleteAlarm(
        userId: String,
        alarmId: String
    ) {
        viewModelScope.launch {
            try {
                repo.deleteAlarm(userId, alarmId)
            } catch (e: Exception) {
                Log.e("Alarm", "Error deleting Alarm $e")
            }
        }
    }
}