package com.example.data.remote

import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.CategoryResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MedicineDataApiService {

    @GET("products/category/{medicines}")
    suspend fun getMedicineFromRemote(
        @Path("medicines")
        medicines: String
    ): Response<CategoryResponse>

    @GET("products/category/امساك")
    suspend fun getMedicineFromEmsaak(): CategoryResponse

    @GET("products/category/الكحة")
    suspend fun getMedicineFromKo7aa(): CategoryResponse

    @GET("products/category/المغص")
    suspend fun getMedicineFromM8aas(): CategoryResponse

    @GET("products/{id}")
    suspend fun getMedicineDetailsFromRemote(
        @Path("id")
        id: String
    ): Response<CategoryResponseItem>

    @GET("products/type/الأدوية")
    suspend fun getMedicineFromAllMedicines(): CategoryResponse
}