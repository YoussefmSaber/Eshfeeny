package com.example.domain.entity.imageResponse

data class Data(
    val delete_url: String,
    val display_url: String,
    val expiration: String,
    val height: String,
    val id: String,
    val image: Image,
    val medium: Medium,
    val size: String,
    val thumb: Thumb,
    val time: String,
    val title: String,
    val url: String,
    val url_viewer: String,
    val width: String
)