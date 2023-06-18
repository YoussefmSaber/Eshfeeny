package com.example.domain.entity

data class Alarm(
    val _id: String,
    val alarmTime: List<String>,
    val days: String?,
    val dose: Int,
    val endDate: String,
    val name: String,
    val notes: String?,
    val repetition: String,
    val startDate: String
)