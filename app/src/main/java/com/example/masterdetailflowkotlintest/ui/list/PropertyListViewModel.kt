package com.example.masterdetailflowkotlintest.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(

    var propertyRepository: PropertyRepository

): ViewModel() {

    val allProperties: LiveData<List<Property>> = propertyRepository.allProperties.asLiveData()


}