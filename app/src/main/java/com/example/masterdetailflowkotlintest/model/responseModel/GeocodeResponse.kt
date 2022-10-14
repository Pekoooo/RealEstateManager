package com.example.masterdetailflowkotlintest.model.responseModel

import com.example.masterdetailflowkotlintest.model.LocationView
import java.net.URLEncoder

data class GeocodeResponse(
    val results: List<Result>,
    val status: String
)
data class Result(
    val geometry: Geometry,
    val place_id: String,
)

data class Geometry(
    val location: Location,
)

data class Location(
    val lat: Double,
    val lng: Double
)

fun GeocodeResponse.toLocationView(): LocationView =
    LocationView(
        lat = results.firstOrNull()?.geometry?.location?.lat ?: 0.0,
        long = results.firstOrNull()?.geometry?.location?.lng ?: 0.0
    )



