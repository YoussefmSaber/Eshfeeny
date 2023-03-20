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
        return try {
            UserRetrofitInstance.userApi.checkEmail(email)
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    "Email Found"
                )
            )
        }
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
    suspend fun verifySignup(
        email: String
    ): VerifyCodeResponse {
        return withContext(Dispatchers.IO){
            try {
                val code = UserRetrofitInstance.userApi.verifySignup(email)
                Log.i("(UserRepoImpl)Verify code: ", code.toString())
                code
            } catch (e: Exception) {
                VerifyCodeResponse("Error")
            }
        }
    }
}