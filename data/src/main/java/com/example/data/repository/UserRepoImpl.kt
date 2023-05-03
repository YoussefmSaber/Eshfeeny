package com.example.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.local.db.user.UserDAO
import com.example.data.local.db.user.model.UserInfo
import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.*
import com.example.domain.entity.insuranceCard.InsuranceCardResponse
import com.example.domain.entity.insuranceCard.InsuranceCardX
import com.example.domain.entity.patchRequestVar.*
import com.example.domain.entity.patchresponse.PatchRequestResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepoImpl(private val userDAO: UserDAO) {

    // used when the user forget his password
    suspend fun checkEmail(
        email: SendToCheckEmail
    ): Response<CheckEmailResponse> {
        return try {
            UserRetrofitInstance.userApi.checkEmail(email)
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    "Email not found"
                )
            )
        }
    }

    // used for login
    suspend fun verifyLogin(
        userData: VerifyLoginResponse
    ): Response<UserInfo> {
        return try {
            UserRetrofitInstance.userApi.verifyLogin(userData)
        } catch (e: Exception) {
            Log.i("Login Repo", e.toString())
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
    ): Response<UserInfo> =
        UserRetrofitInstance.userApi.createNewUser(newUser)


    @SuppressLint("LongLogTag")
    suspend fun verifyCode(
        email: String
    ): VerifyCodeResponse {
        return try {
            val code = UserRetrofitInstance.userApi.verifyCode(email)
            Log.i("(UserRepoImpl)Verify code: ", email)
            Log.i("(UserRepoImpl)Verify code: ", code.toString())
            code
        } catch (e: Exception) {
            VerifyCodeResponse("Error")
        }

    }

    suspend fun updateUserPassword(
        id: String,
        newPassword: ChangePassword
    ): PatchRequestResponse =
        UserRetrofitInstance.userApi.updateUserPassword(id, newPassword)

    suspend fun addUserDataToDatabase(
        userData: UserInfo
    ) {
        userDAO.addUserData(userData)
    }

    suspend fun deleteUserData() {
        userDAO.deleteUserData()
    }

    suspend fun getUserData(): UserInfo = userDAO.getUserData()

    suspend fun getInsuranceCards(
        userId: String
    ): InsuranceCardResponse = UserRetrofitInstance.userApi.getInsuranceCards(userId)

    suspend fun addInsuranceCard(
        userId: String,
        card: InsuranceCardX
    ): PatchRequestResponse = UserRetrofitInstance.userApi.addInsuranceCard(userId, card)
}