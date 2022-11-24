package com.example.masterdetailflowkotlintest.ui.list

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.masterdetailflowkotlintest.enums.CurrencyType
import com.example.masterdetailflowkotlintest.repositories.ConverterRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.ui.TestCoroutineRule
import com.example.masterdetailflowkotlintest.ui.observeForTesting
import com.example.masterdetailflowkotlintest.utils.DummyPropertyProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class PropertyListFragmentTest {

    companion object {
        private val DUMMY_PROPERTY = DummyPropertyProvider.getDummyProperty()
        private val DEFAULT_CURRENCY_TYPE = MutableLiveData(CurrencyType.DOLLAR)
        private val DUMMY_PROPERTY_LIST = DummyPropertyProvider.samplePropertyList

    }

    private val propertyRepository: PropertyRepository = mockk()
    private val converterRepository: ConverterRepository = mockk()

    private val context = Mockito.mock(Application::class.java)


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val testCoroutineRule = TestCoroutineRule()


    @Before
    fun setUp() {

        every { propertyRepository.getPropertyById(1) } returns flowOf(
            DUMMY_PROPERTY
        )

        every { converterRepository.currencyType() } returns DEFAULT_CURRENCY_TYPE

        every { propertyRepository.allProperties } returns flowOf(
            DUMMY_PROPERTY_LIST
        )


    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `assert that flow as liveData is updated`() = runTest {
        getViewModel().allProperties.observeForTesting {
            assertThat(it).isEqualTo(DUMMY_PROPERTY_LIST)
        }
    }

    @Test
    fun `assert that the currency switch works given a value of Dollar first switched to Euro`() {

        //Given
        assertThat(getViewModel().currencyType.value).isEqualTo(CurrencyType.DOLLAR)

        //When
        DEFAULT_CURRENCY_TYPE.postValue(CurrencyType.EURO)

        getViewModel().currencyType.observeForTesting { result ->
            //Then
            assertThat(result).isEqualTo(CurrencyType.EURO)
        }
    }


    private fun getViewModel() = PropertyListViewModel(
        propertyRepository,
        converterRepository
    )

}