package com.example.data.remote

import com.example.domain.entity.CategoryResponse
import retrofit2.http.GET

interface MedicineDataApiService {
    @GET("امساك")
    suspend fun getMedicineFromEmsaak(): CategoryResponse

    @GET("الكحة")
    suspend fun getMedicineFromKo7aa(): CategoryResponse

    @GET("المغص")
    suspend fun getMedicineFromM8aas(): CategoryResponse
    @GET("الحموضة و سوء الهضم")
    suspend fun getMedicineFrom7modaAndSo2Hadm(): CategoryResponse
    @GET("الفيتامينات و المكملات الغذائية")
    suspend fun getMedicineFromVetamenAndMa2kolat(): CategoryResponse
    @GET("تقوية المناعة")
    suspend fun getMedicineFromT2wyaaElmna3a(): CategoryResponse
    @GET("مسكنات")
    suspend fun getMedicineFromMosknaat(): CategoryResponse
    @GET("مضادات حيوية")
    suspend fun getMedicineFromModat7aywee(): CategoryResponse

}