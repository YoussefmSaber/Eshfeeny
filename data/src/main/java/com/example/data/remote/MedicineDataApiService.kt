package com.example.data.remote

import com.example.domain.entity.CategoryResponse
import retrofit2.http.GET

interface MedicineDataApiService {
    @GET("امساك")
    suspend fun getMedicineFromEmsaak(): CategoryResponse

}