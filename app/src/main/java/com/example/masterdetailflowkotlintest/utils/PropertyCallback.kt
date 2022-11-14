package com.example.masterdetailflowkotlintest.utils

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import com.example.masterdetailflowkotlintest.utils.DummyPropertyProvider.Companion.getDummyProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider


class PropertyCallback (
    private val provider: Provider<PropertyDao>
) : RoomDatabase.Callback() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {

        /*val property = getDummyProperty()

        provider.get().insert(property)*/

        val properties = DummyPropertyProvider.samplePropertyList

        provider.get().insertAll(properties)
    }


}
