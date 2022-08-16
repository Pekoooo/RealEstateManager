package com.example.masterdetailflowkotlintest.repositories

import androidx.annotation.WorkerThread
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PropertyRepository @Inject constructor(private val propertyDao: PropertyDao){

    val allProperties: Flow<List<Property>> = propertyDao.getAllProperties()

    @WorkerThread
    suspend fun createProperty(property: Property){
        propertyDao.insert(property)
    }



}