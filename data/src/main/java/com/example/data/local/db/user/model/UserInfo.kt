package com.example.data.local.db.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_details")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val _ID: String,
    val userEmail: String,
    val userPassword: String
)
