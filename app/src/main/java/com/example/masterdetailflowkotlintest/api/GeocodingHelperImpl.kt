package com.example.masterdetailflowkotlintest.api

import com.example.masterdetailflowkotlintest.BuildConfig
import com.example.masterdetailflowkotlintest.model.response.GeocodeResponse
import retrofit2.Response
import javax.inject.Inject

class GeocodingHelperImpl @Inject constructor(
    private val geocodingService: GeocodingService
):GeocodingHelper{

    override suspend fun getLocation(address: String): Response<GeocodeResponse> = geocodingService.getLocation(address, BuildConfig.MAPS_API_KEY)

}