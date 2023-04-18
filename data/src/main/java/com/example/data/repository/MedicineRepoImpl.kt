package com.example.data.repository

import android.util.Log
import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.CategoryResponse
import com.example.domain.entity.CategoryResponseItem
import com.example.domain.entity.patchRequestVar.AddToFavorites
import com.example.domain.entity.patchresponse.PatchRequestResponse
import retrofit2.Response

class MedicineRepoImpl {

    suspend fun getMedicinesFromRemote(medicines: String): Response<CategoryResponse> =
        UserRetrofitInstance.medicineApi.getMedicineFromRemote(medicines)

    //get medicine امساك
    suspend fun getMedicineFromRemoteForEmsaak(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromEmsaak()

    //get medicine كحه
    suspend fun getMedicineFromRemoteForKo7aa(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromKo7aa()

    //get medicine مغص
    suspend fun getMedicineFromRemoteForM8aas(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromM8aas()

    suspend fun getMedicineDetailsFromRemote(id: String): Response<CategoryResponseItem> =
        UserRetrofitInstance.medicineApi.getMedicineDetailsFromRemote(id)

    //get All Medicine
    suspend fun getMedicineFromRemoteForAllMedicines(): CategoryResponse =
        UserRetrofitInstance.medicineApi.getMedicineFromAllMedicines()

    suspend fun addMedicineToFavorites(
        userId: String,
        productId: AddToFavorites
    ): PatchRequestResponse =
        UserRetrofitInstance.medicineApi.addMedicneToFavorite(userId, productId)

    suspend fun getFavoriteProducts(
        userId: String
    ): CategoryResponse =
        UserRetrofitInstance.medicineApi.getFavoriteProducts(userId)

    suspend fun deleteFavoriteProduct(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        UserRetrofitInstance.medicineApi.deleteFavoriteProduct(userId, productId)
}