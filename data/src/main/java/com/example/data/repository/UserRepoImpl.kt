package com.example.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class UserRepoImpl {

    // used when the user forget his password
    suspend fun checkEmail(
        email: String
    ): Response<CheckEmailResponse> {
        val response = UserRetrofitInstance.userApi.checkEmail(email)
        Log.i("Email found: ", response.body().toString())
        return UserRetrofitInstance.userApi.checkEmail(email)
    }

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
    ): Response<NewUserResponse> =
        UserRetrofitInstance.userApi.createNewUser(newUser)


    @SuppressLint("LongLogTag")
    suspend fun verifyCode(
        email: String
    ): VerifyCodeResponse {
        return try {
            val code = UserRetrofitInstance.userApi.verifyCode(email)
            Log.i("(UserRepoImpl)Verify code: ", code.toString())
            code
        } catch (e: Exception) {
            VerifyCodeResponse("Error")
        }

    }

    suspend fun updateUserPassword(
        id: String,
        newPassword: String
    ): PasswordChangeResponse =
        UserRetrofitInstance.userApi.updateUserPassword(id, newPassword)
}