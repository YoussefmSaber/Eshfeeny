package com.example.data.remote

import com.example.domain.entity.CheckEmailResponse
import com.example.domain.entity.UserResponse
import com.example.domain.entity.VerifyLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserDataApiService {

    // this for the user when he forget password
    @GET("users/checkEmail/{email}")
    suspend fun checkEmail(
        @Path("email")
        email: String): CheckEmailResponse

    @POST("users/verify")
    suspend fun verifyLogin(
        @Body
        verifyLoginResponse: VerifyLoginResponse
    ): Response<UserResponse>
}