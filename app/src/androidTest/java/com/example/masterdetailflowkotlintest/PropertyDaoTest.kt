package com.example.masterdetailflowkotlintest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.filters.SmallTest
import com.example.masterdetailflowkotlintest.placeholder.PlaceholderContent
import com.example.masterdetailflowkotlintest.room.LocalDatabase
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * Made by following → https://medium.com/swlh/android-room-testing-made-easy-using-dagger-hilt-89d2d5d0e7f2
 */


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
    fun insertProperty() = runBlocking {
        val property = PlaceholderContent.ITEMS[(1..10).random()]
        propertyDao.insert(property)

        val allProperties = propertyDao.getAllProperties()

        assert(allProperties.first().contains(property))
    }

    @Test
    fun deleteAllProperties() = runBlocking {

        val property = PlaceholderContent.ITEMS[(1..10).random()]
        propertyDao.insert(property)

        val allProperties = propertyDao.getAllProperties()
        propertyDao.deleteAll()

        assert(allProperties.first().isEmpty())
    }
}