package com.example.masterdetailflowkotlintest.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.masterdetailflowkotlintest.model.Property
import kotlinx.coroutines.flow.Flow

interface PropertyDao {

    @Query("SELECT * FROM property_table")
    fun getAllProperties(): Flow<List<Property>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property)
}