package com.example.masterdetailflowkotlintest.room.dao

import androidx.room.*
import com.example.masterdetailflowkotlintest.model.pojo.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property_table")
    fun getAllProperties(): Flow<List<Property>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property)

    @Query("SELECT * FROM property_table WHERE property_id = :id")
    fun getPropertyById(id: Int): Flow<Property>

    @Update
    suspend fun updateProperty(property: Property)


    /**
     * For testing purposes
     */
    @Query("DELETE FROM property_table")
    fun deleteAll()
}