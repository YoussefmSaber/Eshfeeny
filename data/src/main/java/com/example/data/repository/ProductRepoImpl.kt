package com.example.data.repository

import android.util.Log
import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.InsuranceCard.InsuranceCardResponse
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchresponse.PatchRequestResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class ProductRepoImpl {

    suspend fun getProductsFromRemote(medicines: String): Response<ProductResponse> =
        UserRetrofitInstance.medicineApi.getMedicineFromRemote(medicines)

    suspend fun getProductFromRemote(id: String): Response<ProductResponseItem> =
        UserRetrofitInstance.medicineApi.getMedicineDetailsFromRemote(id)

    //get All Medicine
    suspend fun getProductType(productType: String): ProductResponse =
        UserRetrofitInstance.medicineApi.getProductType(productType)

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

    suspend fun getUserCartItems(
        userId: String
    ): CartResponse = UserRetrofitInstance.userApi.getUsersCartItems(userId)

    suspend fun addProductToCart(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse = UserRetrofitInstance.userApi.addProductToCart(userId, productId)

    suspend fun removeProductFromCart(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse {
        return try {
            UserRetrofitInstance.userApi.removeProductFromCart(userId, productId)
        } catch (e: Exception) {
            Log.e("delete cart", e.message.toString())
            UserRetrofitInstance.userApi.removeProductFromCart(userId, productId)
        }
    }

    suspend fun incrementProductNumberInCart(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        UserRetrofitInstance.userApi.incrementProductNumberInCart(userId, productId)

    suspend fun decrementProductNumberInCart(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        UserRetrofitInstance.userApi.decrementProductNumberInCart(userId, productId)

    suspend fun getNumberOfItemInCart(
        userId: String,
        productId: String
    ): Int {
        val resp = UserRetrofitInstance.userApi.getNumberOfItemInCart(userId, productId)
        Log.i("details Fragment", resp.toString())
        return resp
    }

    suspend fun getInsuranceCards(
        userId: String
    ): InsuranceCardResponse = UserRetrofitInstance.userApi.getInsuranceCards(userId)
}