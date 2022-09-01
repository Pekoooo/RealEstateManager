package com.example.masterdetailflowkotlintest.ui.cameraFragment

import android.util.Log
import androidx.lifecycle.*
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CameraFragmentViewModel @Inject constructor(

    private var propertyRepository: PropertyRepository

) : ViewModel() {

    fun getPropertyById(id: Int): Flow<Property> = propertyRepository.getPropertyById(id)

    fun updateProperty(property: Property) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(MainActivity.TAG, "updateProperty: ${property.description} ")
        propertyRepository.updateProperty(property)
    }




}