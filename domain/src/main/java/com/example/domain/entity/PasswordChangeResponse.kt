package com.example.domain.entity

data class PasswordChangeResponse(
    val acknowledged: Boolean,
    val matchedCount: Int,
    val modifiedCount: Int,
    val upsertedCount: Int,
    val upsertedId: Any
)