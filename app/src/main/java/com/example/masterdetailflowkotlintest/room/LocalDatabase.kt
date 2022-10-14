package com.example.masterdetailflowkotlintest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.masterdetailflowkotlintest.model.appModel.Property
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import com.example.masterdetailflowkotlintest.utils.ListConverter


@Database(
    entities = [Property::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class LocalDatabase: RoomDatabase() {


    abstract fun propertyDao(): PropertyDao

}