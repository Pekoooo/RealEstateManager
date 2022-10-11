package com.example.masterdetailflowkotlintest.repositories

import androidx.lifecycle.MutableLiveData
import com.example.masterdetailflowkotlintest.utils.CurrencyType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConverterRepository @Inject constructor() {

    private val currencyTypeLiveData: MutableLiveData<CurrencyType> = MutableLiveData()

     init {
         currencyTypeLiveData.value = CurrencyType.DOLLAR
     }

    fun switchCurrency() {

        currencyTypeLiveData.value = when(currencyTypeLiveData.value){
            CurrencyType.DOLLAR -> CurrencyType.EURO
            CurrencyType.EURO -> CurrencyType.DOLLAR

            else -> { CurrencyType.DOLLAR }
        }

    }

    fun currencyType(): MutableLiveData<CurrencyType> {
        return currencyTypeLiveData
    }

}