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

    //Todo get property with id


    /**
     * For testing purposes
     */
    @Query("DELETE FROM property_table")
    fun deleteAll()
}