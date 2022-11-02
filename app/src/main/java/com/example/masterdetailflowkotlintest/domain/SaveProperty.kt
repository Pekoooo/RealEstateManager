package com.example.masterdetailflowkotlintest.domain

import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.repositories.GeocoderRepository
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import javax.inject.Inject


class SaveProperty @Inject constructor(
    private val propertyDao: PropertyDao,
    private val geocoderRepository: GeocoderRepository
) {

    suspend fun save(property: Property){

        val locationResponse = geocoderRepository.getLocation(property.fullAddress)

        val lat = locationResponse.body()?.results?.get(0)?.geometry?.location?.lat
        val lng = locationResponse.body()?.results?.get(0)?.geometry?.location?.lng

        val propertyToSave = property.copy(lat = lat, lng = lng)

        when(propertyToSave.isNew){
            true -> propertyDao.insert(propertyToSave)
            false -> propertyDao.updateProperty(propertyToSave)
        }

    }

}