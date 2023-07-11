package com.example.domain.entity.alarm

data class AlarmPatchRequest(
    val name: String,
    val notes: String,
    val dose: Int,
    val repetition: String,
    val alarmTime: List<Long>,
    val startDate: String,
    val endDate: String,
    val days: List<String>? = null
)