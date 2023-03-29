package com.example.data.local.db.user.model

import androidx.room.TypeConverter
import com.example.domain.entity.InsuranceCardX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<InsuranceCardX>? {
        val listType = object : TypeToken<List<InsuranceCardX>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<InsuranceCardX>?): String? {
        return Gson().toJson(list)
    }
}