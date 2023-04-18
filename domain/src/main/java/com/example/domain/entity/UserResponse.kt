package com.example.domain.entity

import com.example.domain.entity.cart.Cart

data class UserResponse(
    val _id: String,
    val address: String,
    val age: String,
    val alarms: List<Alarm>,
    val cart: List<Cart>,
    val email: String,
    val favorites: List<String>,
    val gender: String,
    val insuranceCards: List<InsuranceCardX>,
    val name: String,
    val orderHistory: List<OrderHistory>,
    val password: String,
    val phoneNumber: String,
    val searchHistory: List<String>
)