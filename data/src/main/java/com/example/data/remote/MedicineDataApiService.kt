package com.example.data.remote

import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.CategoryResponseItem
import com.example.domain.entity.patchRequestVar.AddToFavorites
import com.example.domain.entity.patchresponse.PatchRequestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @PATCH("users/{id}/favorites")
    suspend fun addMedicneToFavorite(
        @Path("id")
        id: String,
        @Body
        productId: AddToFavorites
    ): PatchRequestResponse

    @GET("products/user/{userId}/favorites")
    suspend fun getFavoriteProducts (
        @Path("userId")
        id: String
    ): CategoryResponse

    @DELETE("users/{userId}/favorites/{productId}")
    suspend fun deleteFavoriteProduct(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): PatchRequestResponse
}