package com.example.masterdetailflowkotlintest.repositories

import com.example.masterdetailflowkotlintest.api.GeocodingHelper
import com.example.masterdetailflowkotlintest.api.GeocodingService
import com.example.masterdetailflowkotlintest.model.response.GeocodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GeocoderRepository @Inject constructor(
    private val geocodingHelper: GeocodingHelper

) {
    suspend fun getLocation(address: String): Response<GeocodeResponse> =
        withContext(Dispatchers.IO) {

            geocodingHelper.getLocation(address)
        }

}
