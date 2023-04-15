package com.example.data.remote

import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.CategoryResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MedicineDataApiService {
    @GET("products/category/امساك")
    suspend fun getMedicineFromEmsaak(): CategoryResponse

    @GET("products/category/الكحة")
    suspend fun getMedicineFromKo7aa(): CategoryResponse

    @GET("products/category/المغص")
    suspend fun getMedicineFromM8aas(): CategoryResponse

    @GET("products/category/الحموضة%20و%20سوء%20الهضم")
    suspend fun getMedicineFrom7modaAndSo2Hadm(): CategoryResponse
    @GET("products/category/الفيتامينات%20و%20المكملات%20الغذائية")
    suspend fun getMedicineFromVetamenAndMa2kolat(): CategoryResponse
    @GET("products/category/تقوية%20المناعة")
    suspend fun getMedicineFromT2wyaaElmna3a(): CategoryResponse
    @GET("products/category/مسكنات")
    suspend fun getMedicineFromMosknaat(): CategoryResponse
    @GET("products/category/مضادات%20حيوية")
    suspend fun getMedicineFromModat7aywee(): CategoryResponse

    @GET("products/type/الأدوية")
    suspend fun getMedicineFromAllMedicines(): CategoryResponse
    @GET("/products/{id}")
    suspend fun getMedicineDetails(@Path("id") id: String): Response<CategoryResponseItem>
}