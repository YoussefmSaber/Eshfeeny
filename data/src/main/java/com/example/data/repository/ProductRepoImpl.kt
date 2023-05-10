package com.example.data.repository

import android.util.Log
import com.example.data.remote.apis.EshfeenyApiInstance
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchresponse.PatchRequestResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import retrofit2.Response

class ProductRepoImpl {

    suspend fun getProductsFromRemote(medicines: String): Response<ProductResponse> =
        EshfeenyApiInstance.productApi.getMedicineFromRemote(medicines)

    suspend fun getProductFromRemote(id: String): Response<ProductResponseItem> =
        EshfeenyApiInstance.productApi.getMedicineDetailsFromRemote(id)

    //get All Medicine
    suspend fun getProductType(productType: String): ProductResponse =
        EshfeenyApiInstance.productApi.getProductType(productType)

    suspend fun addMedicineToFavorites(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse =
        EshfeenyApiInstance.productApi.addProductToFavorite(userId, productId)

    suspend fun getFavoriteProducts(
        userId: String
    ): ProductResponse =
        EshfeenyApiInstance.productApi.getFavoriteProducts(userId)

    suspend fun deleteFavoriteProduct(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        EshfeenyApiInstance.productApi.deleteFavoriteProduct(userId, productId)

    suspend fun getUserCartItems(
        userId: String
    ): CartResponse = EshfeenyApiInstance.userApi.getUsersCartItems(userId)

    suspend fun addProductToCart(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse = EshfeenyApiInstance.userApi.addProductToCart(userId, productId)

    suspend fun removeProductFromCart(
        userId: String,
        productId: String
    ): PatchRequestResponse {
        return try {
            EshfeenyApiInstance.userApi.removeProductFromCart(userId, productId)
        } catch (e: Exception) {
            Log.e("delete cart", e.message.toString())
            EshfeenyApiInstance.userApi.removeProductFromCart(userId, productId)
        }
    }

    suspend fun incrementProductNumberInCart(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        EshfeenyApiInstance.userApi.incrementProductNumberInCart(userId, productId)

    suspend fun decrementProductNumberInCart(
        userId: String,
        productId: String
    ): PatchRequestResponse =
        EshfeenyApiInstance.userApi.decrementProductNumberInCart(userId, productId)

    suspend fun getNumberOfItemInCart(
        userId: String,
        productId: String
    ): Int {
        val resp = EshfeenyApiInstance.userApi.getNumberOfItemInCart(userId, productId)
        Log.i("details Fragment", resp.toString())
        return resp
    }

    suspend fun getAlternativeProducts(productId: String): ProductResponse =
        EshfeenyApiInstance.productApi.getAlternatives(productId)


    suspend fun getBrandItems(brandName: String): ProductResponse =
        EshfeenyApiInstance.productApi.getBrandItems(brandName)

    suspend fun getSearchResults(productName: String): ProductResponse =
        EshfeenyApiInstance.productApi.getSearchResults(productName)

}