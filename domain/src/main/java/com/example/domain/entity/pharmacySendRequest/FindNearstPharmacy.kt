package com.example.domain.entity.pharmacySendRequest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FindNearestPharmacy(
    val products: List<String>
): Parcelable