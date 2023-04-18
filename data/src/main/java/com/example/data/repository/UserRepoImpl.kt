package com.example.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.data.local.db.user.UserDAO
import com.example.data.local.db.user.model.UserInfo
import com.example.data.remote.apis.UserRetrofitInstance
import com.example.domain.entity.*
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.patchRequestVar.ChangePassword
import com.example.domain.entity.patchRequestVar.PatchProductId
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

    suspend fun getUserCartItems(
        userId: String
    ): CartResponse = UserRetrofitInstance.userApi.getUsersCartItems(userId)

    suspend fun addProductToCart(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse = UserRetrofitInstance.userApi.addProductToCart(userId, productId)

    suspend fun removeProductFromCart(
        userId: String,
        productId: PatchProductId
    ): PatchRequestResponse = UserRetrofitInstance.userApi.removeProductFromCart(userId, productId)

    suspend fun incrementProductNumberInCart(
        userId: String,
        productId: String
    ): PatchRequestResponse = UserRetrofitInstance.userApi.incrementProductNumberInCart(userId, productId)

    suspend fun decrementProductNumberInCart(
        userId: String,
        productId: String
    ): PatchRequestResponse = UserRetrofitInstance.userApi.decrementProductNumberInCart(userId, productId)

    suspend fun getNumberOfItemInCart(
        userId: String,
        productId: String
    ): Int = UserRetrofitInstance.userApi.getNumberOfItemInCart(userId, productId)

}