package com.example.masterdetailflowkotlintest.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class ConverterRepository @Inject constructor() {

    private var isDollar: MutableLiveData<Boolean> = MutableLiveData()

     init {
         Log.d(MainActivity.TAG, ": init is called")
         isDollar.value = true
     }

    fun switchCurrency() {
        Log.d(MainActivity.TAG, "switchCurrency: from ${isDollar.value}")

        when(isDollar.value){

            true -> isDollar.value = false
            false -> isDollar.value = true

            else -> {}
        }

        Log.d(MainActivity.TAG, "switchCurrency: to ${isDollar.value}")

    }

    fun isDollar(): MutableLiveData<Boolean> {
        Log.d(MainActivity.TAG, "isDollar: ${isDollar.value}")
        return isDollar
    }

}