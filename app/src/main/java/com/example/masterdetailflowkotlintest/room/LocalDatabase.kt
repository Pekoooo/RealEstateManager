package com.example.masterdetailflowkotlintest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao


@Database(
    entities = [Property::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {


    abstract fun propertyDao(): PropertyDao

}