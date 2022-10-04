package com.example.masterdetailflowkotlintest.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ConverterRepository @Inject constructor() {


    private var isDollar: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isDollar.value = true
    }

    fun switchCurrency() {

        isDollar.value = isDollar.value != true

    }

    fun isDollar(): MutableLiveData<Boolean> {
        return isDollar
    }

}