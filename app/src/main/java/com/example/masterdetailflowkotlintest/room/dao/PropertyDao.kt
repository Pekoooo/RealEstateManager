package com.example.masterdetailflowkotlintest.room.dao

import androidx.room.*
import com.example.masterdetailflowkotlintest.model.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property_table")
    fun getAllProperties(): Flow<List<Property>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property)

    @Query("SELECT * FROM property_table WHERE property_id = :id LIMIT 1")
    fun getPropertyById(id: Int): Flow<Property>

    //Todo @Update query for updating property




    /**
     * For testing purposes
     */
    @Query("DELETE FROM property_table")
    fun deleteAll()
}