package com.example.data.local.db.user.model.converters

import androidx.room.TypeConverter
import com.example.domain.entity.OrderHistory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}