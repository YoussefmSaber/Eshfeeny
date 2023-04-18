package com.example.data.remote

import com.example.data.local.db.user.model.UserInfo
import com.example.domain.entity.*
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.domain.entity.patchRequestVar.ChangePassword
import com.example.domain.entity.patchresponse.PatchRequestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserDataApiService {

    // POST request Functions
    // to check if the email exist and get the id for the user
    @POST("users/checkEmail")
    suspend fun checkEmail(
        @Body
        email: SendToCheckEmail
    ): Response<CheckEmailResponse>

    // check if the user email and password matches the ones in the db
    @POST("users/verify")
    suspend fun verifyLogin(
        @Body
        verifyLoginResponse: VerifyLoginResponse
    ): Response<UserInfo>

    // create user account in the db
    @POST("users/")
    suspend fun createNewUser(
        @Body
        newUser: CreateUser
    ): Response<UserInfo>

    // GET request functions
    // get a code to check if the email exist or not
    @GET("email/{email}")
    suspend fun verifyCode(
        @Path("email")
        email: String
    ): VerifyCodeResponse

    // PATCH request functions
    @PATCH("users/{id}/password")
    suspend fun updateUserPassword(
        @Path("id")
        id: String,
        @Body
        newPassword: ChangePassword
    ): PatchRequestResponse

    @GET("users/{userId}/cart")
    suspend fun getUsersCartItems(
        @Path("userId")
        userId: String
    ): CartResponse

    @PATCH("users/{userId}/cart")
    suspend fun addProductToCart(
        @Path("userId")
        userId: String,
        @Body
        productId: PatchProductId
    ): PatchProductId

    @DELETE("users/{userId}/cart")
    suspend fun removeProductFromCart(
        @Path("userId")
        userId: String,
        @Body
        productId: PatchProductId
    ): PatchRequestResponse

    @PATCH("users/{userId}/cart/{productId}/1")
    suspend fun incrementProductNumberInCart(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): PatchRequestResponse

    @PATCH("users/{userId}/cart/{productId}/-1")
    suspend fun decrementProductNumberInCart(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): PatchRequestResponse

    @GET("products/user/{userId}/cart/{productId}")
    suspend fun getNumberOfItemInCart(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): Int
}