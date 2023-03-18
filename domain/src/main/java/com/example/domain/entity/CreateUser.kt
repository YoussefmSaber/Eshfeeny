package com.example.domain.entity

data class CreateUser (
    val email: String,
    val name: String,
    val password: String,
    val type: String = "user"
        )