package com.example.domain.entity.pharmacyResponse

data class PharmacyResponseItem(
    val _id: String,
    val address: String?,
    val cart: List<Cart>,
    val email: String,
    val favorites: List<String>,
    val geoLocation: GeoLocation,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val products: List<ProductX>
)