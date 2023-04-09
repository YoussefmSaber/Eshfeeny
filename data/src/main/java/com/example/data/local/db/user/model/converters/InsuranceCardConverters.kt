package com.example.data.local.db.user.model.converters

import androidx.room.TypeConverter
import com.example.domain.entity.InsuranceCardX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InsuranceCardConverters {
    @TypeConverter
    fun fromString(value: String): List<InsuranceCardX>? {
        val listType = object : TypeToken<List<InsuranceCardX>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<InsuranceCardX>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}