package com.example.data.remote

import com.example.domain.entity.CategoryResponseItem
import com.example.domain.entity.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface MedicineDataApiService {
    @GET("character")
    suspend fun getMedicineFromEmsaak(): CharacterResponse
}