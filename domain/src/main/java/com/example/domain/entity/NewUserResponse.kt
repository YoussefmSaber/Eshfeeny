package com.example.domain.entity

data class NewUserResponse(
    val _id: String,
    val email: String,
    val name: String,
    val success: Boolean = true
)