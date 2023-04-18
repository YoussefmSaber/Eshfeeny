package com.example.domain.entity.cart

import com.example.domain.entity.cart.CartItem

data class CartResponse(
    val cart: List<CartItem>
)