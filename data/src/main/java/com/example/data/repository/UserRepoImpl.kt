package com.example.data.repository

import android.util.Log
import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class UserRepoImpl {

    // used when the user forget his password
    suspend fun checkEmail(
        email: String
    ): CheckEmailResponse =
        UserRetrofitInstance.userApi.checkEmail(email)

    // used for login
    suspend fun verifyLogin(
        userData: VerifyLoginResponse
    ): Response<UserResponse> {
        return try {
            UserRetrofitInstance.userApi.verifyLogin(userData)
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    e.message ?: "Error Occurred"
                )
            )
        }
    }

    suspend fun createNewUser(
        newUser: CreateUser
    ): Response<NewUserResponse> {
        return try {
            UserRetrofitInstance.userApi.createNewUser(newUser)
        } catch (e: Exception) {
            val errorBody = "Email Already Exists"
            Response.error(
                400,
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    errorBody
                )
            )
        }
    }

    suspend fun verifySignup(
        email: String
    ): String =
        UserRetrofitInstance.userApi.verifySignup(email)

}