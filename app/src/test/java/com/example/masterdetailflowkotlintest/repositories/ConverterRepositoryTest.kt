package com.example.masterdetailflowkotlintest.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.masterdetailflowkotlintest.enums.CurrencyType
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ConverterRepositoryTest {

    private val currencyTypeLiveData = MutableLiveData<CurrencyType>()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun currency_should_be_switched_from_dollar_to_euro() {

        currencyTypeLiveData.postValue(CurrencyType.DOLLAR)

        currencyTypeLiveData.postValue(

            when (currencyTypeLiveData.value) {

                CurrencyType.DOLLAR -> CurrencyType.EURO
                CurrencyType.EURO -> CurrencyType.DOLLAR

                else -> {
                    CurrencyType.DOLLAR
                }

            }

        )
        assertThat(currencyTypeLiveData.value).isEqualTo(CurrencyType.EURO)
    }

    @Test
    fun currency_should_be_switched_from_euro_to_dollar() {
        //Given
        currencyTypeLiveData.postValue(CurrencyType.EURO)

        //When
        currencyTypeLiveData.postValue(

            when (currencyTypeLiveData.value) {

                CurrencyType.DOLLAR -> CurrencyType.EURO
                CurrencyType.EURO -> CurrencyType.DOLLAR

                else -> {
                    CurrencyType.DOLLAR
                }

            }

        )

        //Then
        assertThat(currencyTypeLiveData.value).isEqualTo(CurrencyType.DOLLAR)
    }
}