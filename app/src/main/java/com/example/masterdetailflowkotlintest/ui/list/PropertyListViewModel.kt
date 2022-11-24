package com.example.masterdetailflowkotlintest.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.repositories.ConverterRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.enums.CurrencyType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(

    private val propertyRepository: PropertyRepository,
    private val converterRepository: ConverterRepository

): ViewModel() {

    val allProperties: LiveData<List<Property>> = propertyRepository.allProperties.asLiveData()

    val currencyType: MutableLiveData<CurrencyType> = converterRepository.currencyType()

    fun switchCurrencyUi(){
        converterRepository.switchCurrency()
    }

    fun retrieveAllProperties(): LiveData<List<Property>> {
        return propertyRepository.allProperties.asLiveData()
    }


}