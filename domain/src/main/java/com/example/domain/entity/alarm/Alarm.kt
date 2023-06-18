package com.example.domain.entity.alarm

data class Alarm(
    val _id: String,
    val alarmTime: List<Long>,
    val days: String?,
    val dose: Int,
    val startDate: String,
    val endDate: String,
    val name: String,
    val notes: String?,
    val repetition: String
)