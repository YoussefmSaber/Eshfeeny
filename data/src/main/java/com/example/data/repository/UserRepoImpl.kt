package com.example.data.repository

import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.CheckEmailResponse
import com.example.domain.entity.UserResponse
import com.example.domain.entity.VerifyLoginResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
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
            Response.error(400, ResponseBody.create("application/json".toMediaTypeOrNull(), e.message ?: "Error Occurred"))
        }
    }
}