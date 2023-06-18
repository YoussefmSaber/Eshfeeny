package com.example.data.remote.Service

import com.example.domain.entity.alarm.Alarm
import com.example.domain.entity.alarm.AlarmPatchRequest
import com.example.domain.entity.patchresponse.PatchRequestResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AlarmApiService {

    @GET("users/{userId}/alarms/")
    suspend fun getUserAlarms(
        @Path("userId")
        id: String
    ): List<Alarm>

    @PATCH("users/{userId}/alarms/")
    suspend fun sendAlarm(
        @Path("userId")
        userId: String,
        @Body
        alarmItem: AlarmPatchRequest
    ): PatchRequestResponse

    @PATCH("users/{userId}/alarms/{alarmId}")
    suspend fun editAlarm(
        @Path("userId")
        userId: String,
        @Path("alarmId")
        alarmId: String,
        @Body
        alarmItem: AlarmPatchRequest
    ): PatchRequestResponse

    @DELETE("users/{userId}/alarms/{alarmId}")
    suspend fun deleteAlarm(
        @Path("userId")
        userId: String,
        @Path("alarmId")
        alarmId: String
    ): PatchRequestResponse
}