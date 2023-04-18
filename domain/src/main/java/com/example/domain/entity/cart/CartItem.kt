package com.example.domain.entity.cart

import com.example.domain.entity.product.ProductResponseItem

data class CartItem(
    val product: ProductResponseItem,
    val quantity: Int
)