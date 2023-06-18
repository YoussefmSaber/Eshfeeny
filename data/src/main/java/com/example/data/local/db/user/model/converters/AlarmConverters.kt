package com.example.data.local.db.user.model.converters

import androidx.room.TypeConverter
import com.example.domain.entity.alarm.Alarm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlarmConverters {
    @TypeConverter
    fun fromString(value: String): List<Alarm>? {
        val listType = object : TypeToken<List<Alarm>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Alarm>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}