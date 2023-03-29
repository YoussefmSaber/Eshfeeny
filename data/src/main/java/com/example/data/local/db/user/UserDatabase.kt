package com.example.data.local.db.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.db.user.model.UserInfo
import com.example.data.local.db.user.model.converters.*

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
@TypeConverters(
    InsuranceCardConverters::class,
    AlarmConverters::class,
    CartConverters::class,
    OrderHistoryConverters::class,
    FavoritesListConverter::class,
    SearchHistoryListConverter::class
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}