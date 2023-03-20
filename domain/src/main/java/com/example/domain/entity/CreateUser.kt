package com.example.domain.entity

import android.os.Parcelable

data class CreateUser (
    val email: String,
    val name: String,
    val password: String,
    val type: String = "user"
        )