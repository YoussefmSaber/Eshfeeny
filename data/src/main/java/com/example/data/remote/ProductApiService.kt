package com.example.data.remote

import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchresponse.PatchRequestResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ProductApiService {

    @GET("products/category/{medicines}")
    suspend fun getMedicineFromRemote(
        @Path("medicines")
        medicines: String
    ): Response<ProductResponse>

    @GET("products/{id}")
    suspend fun getMedicineDetailsFromRemote(
        @Path("id")
        id: String
    ): Response<ProductResponseItem>

    @GET("products/type/{productType}")
    suspend fun getProductType(
        @Path("productType")
        productType: String
    ): ProductResponse

    @PATCH("users/{id}/favorites")
    suspend fun addProductToFavorite(
        @Path("id")
        id: String,
        @Body
        productId: PatchProductId
    ): PatchRequestResponse

    @GET("products/user/{userId}/favorites")
    suspend fun getFavoriteProducts(
        @Path("userId")
        id: String
    ): ProductResponse

    @DELETE("users/{userId}/favorites/{productId}")
    suspend fun deleteFavoriteProduct(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): PatchRequestResponse

    @GET("products/alternatives/{productId}")
    suspend fun getAlternatives(
        @Path("productId")
        productId: String
    ): ProductResponse

    @GET("products/brand/{brandName}")
    suspend fun getBrandItems(
        @Path("brandName")
        brandName: String
    ): ProductResponse

    @GET("products/search/{productName}")
    suspend fun getSearchResults(
        @Path("productName")
        productName: String
    ): ProductResponse
}