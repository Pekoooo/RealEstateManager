package com.example.masterdetailflowkotlintest.repositories

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import kotlin.math.pow

class MortgageCalculatorRepositoryTest {

    private val mortgageCalculatorRepository: MortgageCalculatorRepository = MortgageCalculatorRepository()

    @Test
    fun monthly_payment_mortgage_should_return_expected_amount() {
        assertThat(mortgageCalculatorRepository.monthlyPaymentMortgage(
            30000.0,
            5.0,
            5)).isEqualTo(
            566)
    }

}