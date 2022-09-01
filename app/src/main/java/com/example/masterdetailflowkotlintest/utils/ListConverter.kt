package com.example.masterdetailflowkotlintest.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    @TypeConverter
    fun fromPictureList(value: MutableList<String>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPictureList(value: String): MutableList<String> {
        val gson = Gson()
        val type = object  : TypeToken<MutableList<String>>() {}.type
        return gson.fromJson(value, type)
    }
}