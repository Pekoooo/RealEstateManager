package com.example.masterdetailflowkotlintest.ui.mortgageCalculator

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterdetailflowkotlintest.repositories.MortgageCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MortgageCalculatorViewModel @Inject constructor(
    private val mortgageCalculatorRepository: MortgageCalculatorRepository
    ) : ViewModel() {


    /**
     * Mortgage payment repository
     */

    fun getMortgageMonthlyPaymentFee(amount: Double, preferredRate: Double, years: Int): Int {
        return mortgageCalculatorRepository.monthlyPaymentMortgage(amount, preferredRate, years)
    }

    fun getTotalInvestmentCost(monthlyFee: Int, length: Int): Int {
        return mortgageCalculatorRepository.totalInvestmentCost(monthlyFee, length)
    }

}