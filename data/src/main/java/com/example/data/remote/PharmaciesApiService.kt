package com.example.data.remote

import com.example.domain.entity.pharmacyResponse.PharmacyResponse
import com.example.domain.entity.pharmacySendRequest.FindNearestPharmacy
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PharmaciesApiService {

    @GET("pharmacies/")
    suspend fun getAllPharmacies(): PharmacyResponse

    @POST("pharmacies/available")
    suspend fun availablePharmacies(
        @Body
        listProducts: FindNearestPharmacy
    ): PharmacyResponse
}