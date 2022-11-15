package com.example.masterdetailflowkotlintest.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.repositories.ConverterRepository
import com.example.masterdetailflowkotlintest.repositories.LocationRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.utils.CurrencyType
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

    var locationRepository: LocationRepository,
    var propertyRepository: PropertyRepository,
    var converterRepository: ConverterRepository
): ViewModel() {

    fun getUserLocation() {
        locationRepository.getUserLocation()
    }

    fun getLocationLiveData(): MutableLiveData<Location>{
        return locationRepository.getLocationLiveData()
    }

    fun getPropertyWithLatLng(position: LatLng): Property? {
        var propertyToReturn: Property? = null
        for(property in allProperties.value!!){
            if(position == property.latLng){
                propertyToReturn = property
            }
        }
        return propertyToReturn
    }

    val allProperties: LiveData<List<Property>> = propertyRepository.allProperties.asLiveData()

    val currencyType: MutableLiveData<CurrencyType> = converterRepository.currencyType()

}