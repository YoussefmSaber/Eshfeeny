package com.example.domain.entity.patchRequestVar

data class UpdateUserPassword(
    val newPassword: String,
    val oldPassword: String
)
