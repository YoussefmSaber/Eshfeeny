package com.example.domain.entity

import com.squareup.moshi.Json

data class CategoryResponseItem(
    @Json(name="image")
    val image: String,
    @Json(name="name")
    val name: String
)
data class CharacterResponse(
    @Json(name="results")
    val result: List<CategoryResponseItem>
)