package com.example.data.local.db.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.alarm.Alarm
import com.example.domain.entity.cart.Cart
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.domain.entity.OrderHistory

@Entity("user_details")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val _id: String?= null,
    val address: String? = null,
    val age: String? = null,
    val alarms: List<Alarm>? = null,
    val cart: List<Cart>? = null,
    val email: String?= null,
    val favorites: List<String>? = null,
    val gender: String? = null,
    val insuranceCards: List<InsuranceCardX>? = null,
    val name: String?= null,
    val orderHistory: List<OrderHistory>? = null,
    var password: String?= null,
    val phoneNumber: String? = null,
    val searchHistory: List<String>? = null,
    var state: String = "guest"
)