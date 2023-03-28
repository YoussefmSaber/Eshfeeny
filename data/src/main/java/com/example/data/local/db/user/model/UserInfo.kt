package com.example.data.local.db.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.InsuranceCardX

@Entity("user_details")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val _id: String,
    val name: String,
    val password: String,
    val email: String,
    val age: String,
    val gender: String,
    val phoneNumber: String,
    val insuranceCards: List<InsuranceCardX>
)