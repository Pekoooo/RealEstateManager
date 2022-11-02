package com.example.masterdetailflowkotlintest.repositories

import androidx.annotation.WorkerThread
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PropertyRepository @Inject constructor(
    private val propertyDao: PropertyDao
    ){

    val allProperties: Flow<List<Property>> = propertyDao.getAllProperties()

    @WorkerThread
    suspend fun createProperty(property: Property){
        propertyDao.insert(property)
    }

    fun getPropertyById(id: Int): Flow<Property> = propertyDao.getPropertyById(id)

    suspend fun updateProperty(property: Property) = withContext(Dispatchers.IO) {
        propertyDao.updateProperty(property)
    }

}