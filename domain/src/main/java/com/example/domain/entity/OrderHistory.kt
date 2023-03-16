package com.example.domain.entity

data class OrderHistory(
    val _id: String,
    val products: List<ProductX>,
    val total: Double
)