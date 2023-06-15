package com.example.data.remote

import com.example.domain.entity.pharmacyResponse.PharmacyResponse
import retrofit2.http.GET

interface PharmaciesApiService {

    @GET("pharmacies/")
    suspend fun getAllPharmacies(): PharmacyResponse
}