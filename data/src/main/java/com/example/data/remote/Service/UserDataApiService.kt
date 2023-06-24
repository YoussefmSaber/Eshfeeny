package com.example.data.remote.Service

import com.example.data.local.db.user.model.UserInfo
import com.example.domain.entity.*
import com.example.domain.entity.insuranceCard.InsuranceCardResponse
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.insuranceCard.InsuranceCardPatchItem
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.domain.entity.patchRequestVar.PatchString
import com.example.domain.entity.patchRequestVar.ChangePassword
import com.example.domain.entity.patchRequestVar.UpdateUserData
import com.example.domain.entity.patchRequestVar.UpdateUserGender
import com.example.domain.entity.patchRequestVar.UpdateUserPassword
import com.example.domain.entity.patchresponse.PatchRequestResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserDataApiService {

    // POST request Functions
    // to check if the email exist and get the id for the user
    @POST("users/checkEmail")
    suspend fun checkEmail(
        @Body
        email: SendToCheckEmail
    ): Response<CheckEmailResponse>

    // check if the user email and password in the db matches the server one
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

    @GET("users/{userId}/cart")
    suspend fun getUsersCartItems(
        @Path("userId")
        userId: String
    ): CartResponse

    @GET("email/{email}")
    suspend fun verifyCode(
        @Path("email")
        email: String
    ): VerifyCodeResponse

    @GET("products/user/{userId}/cart/{productId}")
    suspend fun getNumberOfItemInCart(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): Int

    @GET("users/{userId}/insuranceCards")
    suspend fun getInsuranceCards(
        @Path("userId")
        userId: String,
    ): InsuranceCardResponse

    @PATCH("users/{userId}/cart")
    suspend fun addProductToCart(
        @Path("userId")
        userId: String,
        @Body
        productId: PatchString
    ): PatchRequestResponse

    // PATCH request functions
    @PATCH("users/{id}/password")
    suspend fun updateUserPassword(
        @Path("id")
        id: String,
        @Body
        newPassword: ChangePassword
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

    @PATCH("users/{userId}/insuranceCards")
    suspend fun addInsuranceCard(
        @Path("userId")
        userId: String,
        @Body
        insuranceCard: InsuranceCardPatchItem
    ): PatchRequestResponse

    @PATCH("users/{userId}/profile")
    suspend fun updateUserNameRemote(
        @Path("userId")
        userId: String,
        @Body
        userData: UpdateUserData
    ): PatchRequestResponse

    @PATCH("users/{userId}/gender")
    suspend fun updateUserGender(
        @Path("userId")
        userId: String,
        @Body
        userGender: UpdateUserGender
    ): PatchRequestResponse

    @PATCH("users/{userId}/compareAndUpdate")
    suspend fun compareAndUpdateUserPassword(
        @Path("userId")
        userId: String,
        @Body
        passwords: UpdateUserPassword
    ): PatchRequestResponse

    // Delete Request Functions
    @DELETE("users/{userId}/cart/{productId}")
    suspend fun removeProductFromCart(
        @Path("userId")
        userId: String,
        @Path("productId")
        productId: String
    ): PatchRequestResponse
}