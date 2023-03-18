package com.example.domain.entity

data class Alarm(
    val _id: String,
    val alarmTime: List<String>,
    val days: Any,
    val dose: Int,
    val endDate: Any,
    val name: String,
    val notes: Any,
    val repetition: String,
    val startDate: String
)