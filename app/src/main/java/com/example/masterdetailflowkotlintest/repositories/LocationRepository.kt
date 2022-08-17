package com.example.masterdetailflowkotlintest.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.log


class LocationRepository @Inject constructor(
    @ApplicationContext context: Context
){

    companion object{
        private const val TAG = "MyLocationRepository"
    }

    private var locationLiveData = MutableLiveData<Location>()

    private var fusedLocationProvider: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    fun getUserLocation(){
        fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
            locationLiveData.value = location
        }
    }

    fun getLocationLiveData(): MutableLiveData<Location>{
        return locationLiveData
    }




}