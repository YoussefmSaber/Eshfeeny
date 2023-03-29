package com.example.data.local.db.user.model.converters

import androidx.room.TypeConverter
import com.example.domain.entity.Cart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartConverters {
    @TypeConverter
    fun fromString(value: String): List<Cart> {
        val listType = object : TypeToken<List<Cart>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Cart>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}