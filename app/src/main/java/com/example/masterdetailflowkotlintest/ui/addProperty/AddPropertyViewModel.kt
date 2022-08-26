package com.example.masterdetailflowkotlintest.ui.addProperty

import android.util.Log
import androidx.lifecycle.*
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(

    var propertyRepository: PropertyRepository

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

     fun getPropertyById(id: Int) {
         viewModelScope.launch {
             propertyRepository.getPropertyById(id).collect { response ->
                 property.value = response
             }
         }
    }

}