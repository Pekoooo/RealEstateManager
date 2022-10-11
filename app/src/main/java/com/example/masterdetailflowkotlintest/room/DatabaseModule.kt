package com.example.masterdetailflowkotlintest.room

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.masterdetailflowkotlintest.room.dao.PropertyDao
import com.example.masterdetailflowkotlintest.utils.PropertyCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

/**
 * This annotation tells Hilt that the dependencies provided
 * via this module shall stay alive as long as application is running.
 */
@InstallIn(SingletonComponent::class)

/**
 * This annotation tells Hilt that this class
 * contributes to dependency injection object graph.
 */
@Module

class DatabaseModule {

    /**
     * This annotation marks the method
     * providePropertyDao as a provider of PropertyDao.
     */

    @Singleton
    @Provides
    fun providePropertyDao(localDatabase: LocalDatabase): PropertyDao {
            return localDatabase.propertyDao()
        }


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context,
    provider: Provider<PropertyDao>) =
        Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "LocalDatabase"
        )
            .addCallback(
                PropertyCallback(provider)
            )
            .build()
}