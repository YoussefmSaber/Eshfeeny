package com.example.domain.entity

import com.example.domain.entity.insuranceCard.InsuranceCardX

data class CheckEmailResponse(
    val _id: String,
    val age: String,
    val email: String,
    val gender: String,
    val insuranceCards: List<InsuranceCardX>,
    val name: String,
    val phoneNumber: String
)