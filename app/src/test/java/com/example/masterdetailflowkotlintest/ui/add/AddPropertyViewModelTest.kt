package com.example.masterdetailflowkotlintest.ui.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.masterdetailflowkotlintest.domain.SaveProperty
import com.example.masterdetailflowkotlintest.repositories.GeocoderRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.ui.TestCoroutineRule
import com.example.masterdetailflowkotlintest.ui.observeForTesting
import com.example.masterdetailflowkotlintest.utils.DummyPropertyProvider
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


class AddPropertyViewModelTest {

    companion object {
        private val DUMMY_PROPERTY = DummyPropertyProvider.getDummyProperty()

    }

    private val propertyRepository: PropertyRepository = mockk()
    private val geocoderRepository: GeocoderRepository = mockk()
    private val saveProperty: SaveProperty = mockk()

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


    private fun getViewModel() = AddPropertyViewModel(
        propertyRepository,
        geocoderRepository,
        saveProperty
    )

}