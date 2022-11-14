package com.example.masterdetailflowkotlintest.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.masterdetailflowkotlintest.room.LocalDatabase
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import com.example.masterdetailflowkotlintest.utils.DummyPropertyProvider
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class PropertyDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: LocalDatabase
    private lateinit var propertyDao: PropertyDao

    @Before
    fun setup() {
        hiltRule.inject()
        propertyDao = database.propertyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun classes_injected_with_success() {
        assertThat(propertyDao).isNotNull()
    }

    @Test
    fun getPropertyById() = runBlocking {
        val properties = DummyPropertyProvider.samplePropertyList
        val expectedProperty = properties[0]
        propertyDao.insertAll(properties)
        val allProperties = propertyDao.getAllProperties().first()

        propertyDao.getPropertyById(properties[0].id)

        assertThat(allProperties[0].id).isEqualTo(expectedProperty.id)
    }

    @Test
    fun insertProperty() = runBlocking {
        val property = DummyPropertyProvider.getDummyProperty()
        propertyDao.insert(property)
        val allUsers = propertyDao.getAllProperties().first()
        assertThat(allUsers).contains(property)
    }

    @Test
    fun insertAll() = runBlocking {
        val allPropertiesInserted = DummyPropertyProvider.samplePropertyList
        propertyDao.insertAll(allPropertiesInserted)

        val allPropertiesFetched = propertyDao.getAllProperties().first()

        assertThat(allPropertiesFetched).isEqualTo(allPropertiesInserted)
    }

    @Test
    fun updateProperty() = runBlocking {
        val property = DummyPropertyProvider.getDummyProperty()

        val propertyToUpdate = property.copy(surface = 1000)

        propertyDao.insert(property)
        propertyDao.updateProperty(propertyToUpdate)

        val updatedProperty = propertyDao.getAllProperties().first()[0]

        assertThat(propertyDao.getAllProperties().first().size).isEqualTo(1)
        assertThat(updatedProperty.surface).isEqualTo(1000)
    }

    @Test
    fun getPropertyResearch() = runBlocking {

        val allProperties = DummyPropertyProvider.samplePropertyList
        propertyDao.insertAll(allProperties)

        val expectedAmountOfHouseFetched = 1
        val isNearTypeProperty = listOf("House", "Flat", "Mansion", "Penthouse")
        val isNearCity = listOf("Coignières", "Golden", "Vancouver", "Niseko", "San Diego")
        val isNearNeighbourhood = listOf("Yvelines", "Blaeberry", "Kits", "Higashiyama", "California")
        val isNearMinPrice = 0
        val isNearMaxPrice = 1_500_000
        val isNearMinSurface = 300
        val isNearMaxSurface = 351

        val fetchedProperties = propertyDao.getPropertyResearch(
            isNearTypeProperty,
            isNearCity,
            isNearNeighbourhood,
            isNearMinPrice,
            isNearMaxPrice,
            isNearMinSurface,
            isNearMaxSurface
        ).first()

        assertThat(fetchedProperties.size).isEqualTo(expectedAmountOfHouseFetched)
        assertThat(fetchedProperties[0].surface).isAtLeast(300)
        assertThat(fetchedProperties[0].surface).isAtMost(351)
        assertThat(fetchedProperties[0].price).isAtMost(1_500_000)

    }

    @Test
    fun deleteAllProperties() = runBlocking {

        val property = DummyPropertyProvider.getDummyProperty()
        propertyDao.insert(property)

        val allProperties = propertyDao.getAllProperties()
        propertyDao.deleteAll()

        assert(allProperties.first().isEmpty())
    }

}