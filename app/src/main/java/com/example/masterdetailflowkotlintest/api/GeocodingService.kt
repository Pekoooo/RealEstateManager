package com.example.masterdetailflowkotlintest.api

import com.example.masterdetailflowkotlintest.model.response.GeocodeResponse
import retrofit2.Response


import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

@GET("/maps/api/geocode/json?")
    suspend fun getLocation(@Query("address") address: String,
                            @Query("key") key: String
    ): Response<GeocodeResponse>


}
