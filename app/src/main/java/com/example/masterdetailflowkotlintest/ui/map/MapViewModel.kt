package com.example.masterdetailflowkotlintest.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.repositories.LocationRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

    var locationRepository: LocationRepository,
    var propertyRepository: PropertyRepository

): ViewModel() {

    fun getUserLocation() {
        locationRepository.getUserLocation()
    }

    fun getLocationLiveData(): MutableLiveData<Location>{
        return locationRepository.getLocationLiveData()
    }

    val allProperties: LiveData<List<Property>> = propertyRepository.allProperties.asLiveData()

}