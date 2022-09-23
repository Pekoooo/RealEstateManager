package com.example.masterdetailflowkotlintest.ui.addProperty

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
class AddPropertyViewModel @Inject constructor(

    private var propertyRepository: PropertyRepository

) : ViewModel() {

    companion object {
        const val TAG = "MyPropertyViewModel"
    }

    val allProperties: LiveData<List<Property>> = propertyRepository.allProperties.asLiveData()
    var property: MutableLiveData<Property> = MutableLiveData()


    fun createProperty(property: Property) = viewModelScope.launch {
        Log.d(TAG, "createProperty: ${property.description}")
        propertyRepository.createProperty(property)
    }

    fun updateProperty(property: Property) = viewModelScope.launch(Dispatchers.IO) {
        propertyRepository.updateProperty(property)
    }

    fun getPropertyById(id: Int): Flow<Property> = propertyRepository.getPropertyById(id)


}