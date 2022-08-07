package com.example.masterdetailflowkotlintest.ui.map

import androidx.lifecycle.ViewModel
import com.example.masterdetailflowkotlintest.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

    var locationRepository: LocationRepository

): ViewModel() {

    fun getUserLocation() {
        locationRepository.getUserLocation()
    }

}