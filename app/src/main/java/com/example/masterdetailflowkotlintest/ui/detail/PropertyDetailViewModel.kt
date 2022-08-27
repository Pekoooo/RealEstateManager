package com.example.masterdetailflowkotlintest.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PropertyDetailViewModel @Inject constructor(

    var propertyRepository: PropertyRepository

) : ViewModel() {


     fun getPropertyById(id: Int): Flow<Property> = propertyRepository.getPropertyById(id)


}