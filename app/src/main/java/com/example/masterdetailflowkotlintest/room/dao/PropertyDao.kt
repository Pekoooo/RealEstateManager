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

    @Query(
        "SELECT * FROM property_table WHERE property_type IN (:isNearTypeProperty)" +
                "AND property_city IN (:isNearCity)" +
                "AND property_neighborhood IN (:isNearNeighbourhood)" +
                "AND property_price BETWEEN :isNearMinPrice AND :isNearMaxPrice " +
                "AND property_surface BETWEEN :isNearMinSurface AND :isNearMaxSurface "



    )
    fun getPropertyResearch(
        isNearTypeProperty: List<String>,
        isNearCity: List<String>,
        isNearNeighbourhood: List<String>,
        isNearMinPrice: Int,
        isNearMaxPrice: Int,
        isNearMinSurface: Int,
        isNearMaxSurface: Int
    ): Flow<List<Property>>


    /**
     * For testing purposes
     */
    @Query("DELETE FROM property_table")
    fun deleteAll()
}