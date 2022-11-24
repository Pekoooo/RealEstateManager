package com.example.masterdetailflowkotlintest.ui.detail

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.view.size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.masterdetailflowkotlintest.domain.SaveProperty
import com.example.masterdetailflowkotlintest.enums.CurrencyType
import com.example.masterdetailflowkotlintest.repositories.ConverterRepository
import com.example.masterdetailflowkotlintest.repositories.GeocoderRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.ui.TestCoroutineRule
import com.example.masterdetailflowkotlintest.ui.add.AddPropertyViewModel
import com.example.masterdetailflowkotlintest.ui.observeForTesting
import com.example.masterdetailflowkotlintest.utils.DummyPropertyProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class PropertyDetailViewModelTest {

    companion object {
        private val DUMMY_PROPERTY = DummyPropertyProvider.getDummyProperty()
        private val DEFAULT_CURRENCY_TYPE = MutableLiveData(CurrencyType.DOLLAR)
        private const val EXPECTED_ADDRESS = "37 allée de la venerie,78310,France"
        private const val EXPECTED_STATIC_MAP_URL =
            "https://maps.googleapis.com/maps/api/staticmap?&center=48.75320809022753,1.9166888529994939&zoom=18&size=400x400&markers=color:red|label:S|48.75320809022753,1.9166888529994939&key=AIzaSyBQHwCHvPrRc_ErGtrWenVO8TJ0iyQg-cY"

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


    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mock_should_return_specific_property() = runTest {
        val expectedProperties = getViewModel().getPropertyById(1)
        assertThat(expectedProperties.first()).isEqualTo(DUMMY_PROPERTY)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `assert that flow as liveData is updated`() = runTest {

        getViewModel().getPropertyById(1).asLiveData().observeForTesting { result ->
            assertThat(result).isEqualTo(DUMMY_PROPERTY)
        }
    }

    @Test
    fun `assert that the currency switch works given a value of Dollar first switched to Euro`() {

        //Given
        assertThat(getViewModel().currencyType().value).isEqualTo(CurrencyType.DOLLAR)

        //When
        DEFAULT_CURRENCY_TYPE.postValue(CurrencyType.EURO)

        getViewModel().currencyType().observeForTesting { result ->
            //Then
            assertThat(result).isEqualTo(CurrencyType.EURO)
        }
    }

    @Test
    fun `assert that methods returns properly formatted static map URL with property info`() {
        val staticUrl = getViewModel().getStaticMapUrl(DUMMY_PROPERTY)

        assertThat(staticUrl).isEqualTo(EXPECTED_STATIC_MAP_URL)
    }

    @Test
    fun `create address should return expected address`() {

        val fullAddress = getViewModel().createAddress(DUMMY_PROPERTY)

        assertThat(fullAddress).isEqualTo(EXPECTED_ADDRESS)
    }


    private fun getViewModel() = PropertyDetailViewModel(
        propertyRepository,
        converterRepository
    )

}