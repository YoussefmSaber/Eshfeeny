package com.example.data.local.db.model

import androidx.room.Entity

@Entity("user_details")
data class UserInfo(
    val id: String,
    val userEmail: String,
    val userPassword: String
)
