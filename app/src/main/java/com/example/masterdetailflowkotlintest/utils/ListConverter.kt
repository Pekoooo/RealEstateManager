package com.example.masterdetailflowkotlintest.utils

import androidx.room.TypeConverter
import com.example.masterdetailflowkotlintest.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    @TypeConverter
    fun fromPictureList(value: MutableList<Photo>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Photo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPictureList(value: String): MutableList<Photo> {
        val gson = Gson()
        val type = object  : TypeToken<MutableList<Photo>>() {}.type
        return gson.fromJson(value, type)
    }
}