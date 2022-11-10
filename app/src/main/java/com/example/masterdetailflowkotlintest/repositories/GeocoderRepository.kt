package com.example.masterdetailflowkotlintest.repositories

import com.example.masterdetailflowkotlintest.api.GeocodingHelper
import com.example.masterdetailflowkotlintest.api.GeocodingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GeocoderRepository @Inject constructor(
private val geocodingHelper: GeocodingHelper

) {
    suspend fun getLocation(address: String) = withContext(Dispatchers.IO){
        geocodingHelper.getLocation(address)
    }

}
