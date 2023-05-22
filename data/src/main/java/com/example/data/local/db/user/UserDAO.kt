package com.example.data.local.db.user

import androidx.room.*
import com.example.data.local.db.user.model.UserInfo

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserData(
        userData: UserInfo
    )

    @Query("SELECT * FROM user_details")
    suspend fun getUserData(): UserInfo

    @Query("DELETE FROM user_details")
    suspend fun deleteUserData()

    @Query("UPDATE user_details SET name = :newName WHERE id = :userId")
    suspend fun updateUserName(
        newName: String,
        userId: Int
    )

    @Query("UPDATE user_details SET password = :newPassword WHERE id = :userId")
    suspend fun updateUserPassword(
        newPassword: String,
        userId: Int
    )

    @Query("UPDATE user_details SET phoneNumber = :newPhone WHERE id = :userId")
    suspend fun updateUserPhoneNumber(
        newPhone: String,
        userId: Int
    )

    @Query("UPDATE user_details SET email = :newEmail WHERE id = :userId")
    suspend fun updateUserEmail(
        newEmail: String,
        userId: Int
    )

    @Query("UPDATE user_details SET gender = :newGender WHERE id = :userId")
    suspend fun updateUserGender(
        newGender: String,
        userId: Int
    )
}