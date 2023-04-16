package com.example.domain.entity.patchresponse

data class PatchRequestResponse(
    val acknowledged: Boolean,
    val matchedCount: Int,
    val modifiedCount: Int,
    val upsertedCount: Int,
    val upsertedId: Any
)