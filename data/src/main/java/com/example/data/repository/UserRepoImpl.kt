package com.example.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.local.db.user.UserDAO
import com.example.data.local.db.user.model.UserInfo
import com.example.data.remote.apis.EshfeenyApiInstance
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
            EshfeenyApiInstance.userApi.checkEmail(email)
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
            EshfeenyApiInstance.userApi.verifyLogin(userData)
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
        EshfeenyApiInstance.userApi.createNewUser(newUser)

    @SuppressLint("LongLogTag")
    suspend fun verifyCode(
        email: String
    ): VerifyCodeResponse {
        return try {
            val code = EshfeenyApiInstance.userApi.verifyCode(email)
            code
        } catch (e: Exception) {
            VerifyCodeResponse("Error")
        }

    }

    suspend fun updateUserPasswordNotLogin(
        id: String,
        newPassword: ChangePassword
    ) =
        EshfeenyApiInstance.userApi.updateUserPassword(id, newPassword)

    suspend fun addUserDataToDatabase(
        userData: UserInfo
    ) {
        userDAO.addUserData(userData)
    }

    suspend fun deleteUserData() {
        userDAO.deleteUserData()
    }

    suspend fun updateUserPasswordLocal(newPassword: String, userId: Int) {
        userDAO.updateUserPassword(newPassword, userId)
    }

    suspend fun getUserData(): UserInfo = userDAO.getUserData()

    suspend fun getInsuranceCards(
        userId: String
    ): InsuranceCardResponse {
        return try {
            EshfeenyApiInstance.userApi.getInsuranceCards(userId)
        } catch (e: Exception) {
            Log.e("card", "Error fetching the insurance card items $e")
            EshfeenyApiInstance.userApi.getInsuranceCards(userId)
        }
    }

    suspend fun addInsuranceCard(
        userId: String,
        card: InsuranceCardX
    ) {
        EshfeenyApiInstance.userApi.addInsuranceCard(userId, card)
    }

    suspend fun updateUserData(
        userId: String,
        newUserData: UpdateUserData,
        userIdLocal: Int
    ) {
        try {
            userDAO.updateUserEmail(newUserData.email, userIdLocal)
            userDAO.updateUserPhoneNumber(newUserData.phoneNumber, userIdLocal)
            userDAO.updateUserName(newUserData.name, userIdLocal)
            EshfeenyApiInstance.userApi.updateUserNameRemote(userId, newUserData)
            Log.i("Update user", "Operation Success!")
        } catch (e: Exception) {
            Log.i("update data Error", "$e")
        }
    }

    suspend fun updateUserGender(
        userIdRemote: String,
        newGender: String,
        userIdLocal: Int
    ) {
        try {
            userDAO.updateUserGender(newGender, userIdLocal)
            EshfeenyApiInstance.userApi.updateUserGender(userIdRemote, UpdateUserGender(newGender))
            Log.i("Update user", "Operation Success!")
        } catch (e: Exception) {
            Log.i("update gender Error", "$e")
        }
    }

    suspend fun updateAndCompareUserPassword(
        newPassword: String,
        oldPassword: String,
        userId: String,
        userIdLocal: Int
    ) {
        try {
            EshfeenyApiInstance.userApi.compareAndUpdateUserPassword(
                userId,
                UpdateUserPassword(newPassword, oldPassword)
            )
            Log.i("Update user Password: ", "Operation Success!")
        } catch (e: Exception) {
            Log.i("Update user Password: ", "Operation Failure :'(")
        }
    }

}