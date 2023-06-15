package com.example.domain.entity.pharmacyResponse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Cart(
    val product: Product,
    val quantity: Int
)