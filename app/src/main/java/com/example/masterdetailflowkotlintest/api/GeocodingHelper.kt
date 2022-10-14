package com.example.masterdetailflowkotlintest.api

import com.example.masterdetailflowkotlintest.model.responseModel.GeocodeResponse
import retrofit2.Response

interface GeocodingHelper {

    suspend fun getLocation(address: String): Response<GeocodeResponse>
}