package com.example.masterdetailflowkotlintest.ui.filteredSearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.masterdetailflowkotlintest.model.pojo.Property
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


internal class FilteredSearchViewModelTest {

    companion object {
        val DEFAULT_NULL_PROPERTY_MUTABLE_LIVE_DATA = MutableLiveData<List<Property>>()

        val DEFAULT_PROPERTY_MUTABLE_LIVE_DATA = MutableLiveData(
            DummyPropertyProvider.samplePropertyList
        )

        val DUMMY_CITY = listOf(
            "Coignières",
            "Golden",
            "Vancouver",
            "Niseko",
            "San Diego"
        )

        val DUMMY_NEIGHBORHOOD = listOf(
            "Yvelines",
            "Blaeberry",
            "Kits",
            "Higashiyama",
            "California"
        )

        private val DUMMY_TYPE_PROPERTY = listOf(
            "House",
            "Flat",
            "Penthouse",
            "Mansion"
        )

        val DUMMY_FILTERED_LIST = DummyPropertyProvider.samplePropertyList

        val isNearTypeProperty: List<String> = DUMMY_TYPE_PROPERTY
        val isNearCity: List<String> = DUMMY_CITY
        val isNearNeighbourhood: List<String> = DUMMY_NEIGHBORHOOD
        const val isNearMinPrice = 0
        const val isNearMaxPrice = 400_000
        const val isNearMinSurface = 0
        const val isNearMaxSurface = 500
    }

    private val propertyRepository: PropertyRepository = mockk()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        every { propertyRepository.allProperties } returns flowOf(
            DummyPropertyProvider.samplePropertyList
        )

        every {
            propertyRepository.getPropertyResearch(
                isNearTypeProperty,
                isNearCity,
                isNearNeighbourhood,
                isNearMinPrice,
                isNearMaxPrice,
                isNearMinSurface,
                isNearMaxSurface
            )
        } returns flowOf(
            DUMMY_FILTERED_LIST
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `assert that all properties are observed and updated`() = runTest {

        //Given
        assertThat(DEFAULT_NULL_PROPERTY_MUTABLE_LIVE_DATA.value).isNull()

        //When
        DEFAULT_NULL_PROPERTY_MUTABLE_LIVE_DATA.postValue(
            getViewModel().returnAllProperties().first() as MutableList<Property>?
        )

        //Then
        DEFAULT_NULL_PROPERTY_MUTABLE_LIVE_DATA.observeForTesting {
            assertThat(it).isNotEmpty()
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `assert that get property research query updates livedata with expected results`() =
        runTest {
            val temp: MutableList<Property> = mutableListOf()
            temp.addAll(
                propertyRepository.getPropertyResearch(
                    isNearTypeProperty,
                    isNearCity,
                    isNearNeighbourhood,
                    isNearMinPrice,
                    isNearMaxPrice,
                    isNearMinSurface,
                    isNearMaxSurface

                ).first()
            )
            DEFAULT_NULL_PROPERTY_MUTABLE_LIVE_DATA.value = temp

            DEFAULT_NULL_PROPERTY_MUTABLE_LIVE_DATA.observeForTesting {
                assertThat(it).isEqualTo(temp)
            }
        }

    @Test
    fun `assert that if query has empty neighborhood filtered list, query sends all the neighborhoods`() {
        val list: MutableList<String> = mutableListOf()
        val listP = DEFAULT_PROPERTY_MUTABLE_LIVE_DATA.value

        for (item in listP!!) {
            if (!list.contains(item.neighborhood)) list.add(item.neighborhood)
        }
        assertThat(list).isEqualTo(DUMMY_NEIGHBORHOOD)
    }

    @Test
    fun`assert that if query has empty type list, query sends all the types in the list`(){
        val list: MutableList<String> = mutableListOf()
        val listP = DEFAULT_PROPERTY_MUTABLE_LIVE_DATA.value

        for (item in listP!!) {
            if (!list.contains(item.type)) list.add(item.type)
        }
        assertThat(list).isEqualTo(DUMMY_TYPE_PROPERTY)
    }

    @Test
    fun `assert that if query has empty city list, query sends all the city in the list`(){
        val list: MutableList<String> = mutableListOf()
        val listP = DEFAULT_PROPERTY_MUTABLE_LIVE_DATA.value

        for (item in listP!!) {
            if (!list.contains(item.city)) list.add(item.city)
        }
        assertThat(list).isEqualTo(DUMMY_CITY)
    }

    @Test
    fun `assert that if query has empty min surface, query is 0, else return query`(){
        val shouldBeNull = getViewModel().buildMinSurface(null)
        val shouldNotBeNull = getViewModel().buildMinSurface(100)

        assertThat(shouldBeNull).isEqualTo(0)
        assertThat(shouldNotBeNull).isEqualTo(100)
    }

    @Test
    fun `assert that if query has empty max surface, query is 1000000, else return 0`(){
        val shouldBeHighNumber = getViewModel().buildMaxSurface(null)
        val shouldBeExpectedNumber = getViewModel().buildMaxSurface(50)

        assertThat(shouldBeHighNumber).isEqualTo(1000000)
        assertThat(shouldBeExpectedNumber).isEqualTo(50)

    }

    @Test
    fun `assert that if query has empty min price, query is 0, else return query`(){
        val shouldBeZero = getViewModel().buildMinPrice(null)
        val shouldBeExpectedNumber = getViewModel().buildMinPrice(50000)

        assertThat(shouldBeZero).isEqualTo(0)
        assertThat(shouldBeExpectedNumber).isEqualTo(50000)
    }

    @Test
    fun `assert that if query has empty max price, query is 100000000, else return query`(){
        val shouldBeHighNumber = getViewModel().buildMaxPrice(null)
        val shouldBeExpectedNumber = getViewModel().buildMaxPrice(50000)

        assertThat(shouldBeHighNumber).isEqualTo(100000000)
        assertThat(shouldBeExpectedNumber).isEqualTo(50000)

    }


    private fun getViewModel() = FilteredSearchViewModel(
        propertyRepository
    )
}