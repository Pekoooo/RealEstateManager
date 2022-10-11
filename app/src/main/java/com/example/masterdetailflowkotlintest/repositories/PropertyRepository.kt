package com.example.masterdetailflowkotlintest.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PropertyRepository @Inject constructor(
    private val propertyDao: PropertyDao
    ){

    val allProperties: Flow<List<Property>> = propertyDao.getAllProperties()

    @WorkerThread
    suspend fun createProperty(property: Property){
        propertyDao.insert(property)
    }

    fun getPropertyById(id: Int): Flow<Property> = propertyDao.getPropertyById(id)

    suspend fun updateProperty(property: Property) {
        propertyDao.updateProperty(property)
    }

}