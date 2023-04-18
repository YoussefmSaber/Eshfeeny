package com.example.domain.entity.product

data class ProductResponseItem(
    val _id: String,
    val activeIngredient: List<String>,
    val amount: String,
    val brand: String,
    val category: List<String>,
    val description: String,
    val images: List<String>,
    val nameAr: String,
    val nameEn: String,
    val price: Double,
    val sideEffects: List<String>,
    val type: String,
    val usage: List<String>,
    val useCases: List<String>,
    val volume: String,
    val warning: List<String>
)