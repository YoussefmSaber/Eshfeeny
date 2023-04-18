package com.example.data.repository

import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchresponse.PatchRequestResponse
import retrofit2.Response

class MedicineRepoImpl {

    suspend fun getMedicinesFromRemote(medicines: String): Response<ProductResponse> =
        UserRetrofitInstance.medicineApi.getMedicineFromRemote(medicines)

    //get medicine امساك
    suspend fun getMedicineFromRemoteForEmsaak(): ProductResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromEmsaak()

    //get medicine كحه
    suspend fun getMedicineFromRemoteForKo7aa(): ProductResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromKo7aa()

    //get medicine مغص
    suspend fun getMedicineFromRemoteForM8aas(): ProductResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromM8aas()

    suspend fun getMedicineDetailsFromRemote(id: String): Response<ProductResponseItem> =
        UserRetrofitInstance.medicineApi.getMedicineDetailsFromRemote(id)

    //get All Medicine
    suspend fun getMedicineFromRemoteForAllMedicines(): ProductResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromAllMedicines()

    suspend fun addMedicineToFavorites(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse =
        UserRetrofitInstance.medicineApi.addMedicneToFavorite(userId, productId)

    suspend fun getFavoriteProducts(
        userId: String
    ): ProductResponse =
        UserRetrofitInstance.medicineApi.getFavoriteProducts(userId)

    suspend fun deleteFavoriteProduct(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        UserRetrofitInstance.medicineApi.deleteFavoriteProduct(userId, productId)
}