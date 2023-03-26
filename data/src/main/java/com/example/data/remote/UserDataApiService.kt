package com.example.data.remote

import com.example.domain.entity.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
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
    ): Response<UserResponse>

    // create user account in the db
    @POST("users/")
    suspend fun createNewUser(
        @Body
        newUser: CreateUser
    ): Response<NewUserResponse>

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
    ): PasswordChangeResponse
}