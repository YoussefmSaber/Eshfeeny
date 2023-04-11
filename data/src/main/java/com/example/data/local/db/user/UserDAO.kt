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
}