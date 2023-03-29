package com.example.data.local.db.user.model.converters

import androidx.room.TypeConverter
import com.example.domain.entity.OrderHistory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderHistoryConverters {
    @TypeConverter
    fun fromString(value: String): List<OrderHistory> {
        val listType = object : TypeToken<List<OrderHistory>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<OrderHistory>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}