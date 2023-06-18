package com.example.data.repository

import com.example.data.remote.apis.EshfeenyApiInstance
import com.example.domain.entity.alarm.AlarmPatchRequest

class AlarmRepoImpl {

    suspend fun getAlarms(
        userId: String
    ) {
        EshfeenyApiInstance.alarmApi.getUserAlarms(userId)
    }

    suspend fun sendAlarm(
        userId: String,
        alarmItem: AlarmPatchRequest
    ) {
        EshfeenyApiInstance.alarmApi.sendAlarm(userId, alarmItem)
    }

    suspend fun editAlarm(
        userId: String,
        alarmId: String,
        alarmItem: AlarmPatchRequest
    ) {
        EshfeenyApiInstance.alarmApi.editAlarm(userId, alarmId, alarmItem)
    }

    suspend fun deleteAlarm(
        userId: String,
        alarmId: String
    ) {
        EshfeenyApiInstance.alarmApi.deleteAlarm(userId, alarmId)
    }
}