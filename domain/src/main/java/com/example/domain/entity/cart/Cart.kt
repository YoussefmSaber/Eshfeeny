package com.example.domain.entity.cart

import com.example.domain.entity.Product

data class Cart(
    val product: Product,
    val quantity: Int
)