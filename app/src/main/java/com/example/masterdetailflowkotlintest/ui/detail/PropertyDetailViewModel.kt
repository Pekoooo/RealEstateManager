package com.example.masterdetailflowkotlintest.ui.detail


import com.example.masterdetailflowkotlintest.utils.Resource
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.masterdetailflowkotlintest.BuildConfig
import com.example.masterdetailflowkotlintest.model.view.LocationView
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.model.response.toLocationView
import com.example.masterdetailflowkotlintest.repositories.ConverterRepository
import com.example.masterdetailflowkotlintest.repositories.GeocoderRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.Constants
import com.example.masterdetailflowkotlintest.utils.CurrencyType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PropertyDetailViewModel @Inject constructor(

    private var propertyRepository: PropertyRepository,
    converterRepository: ConverterRepository,
    private var geocoderRepository: GeocoderRepository



) : ViewModel() {

    private val _locationViewLiveData = MutableLiveData<Resource<LocationView>>()

    val locationViewLiveData : LiveData<Resource<LocationView>>
        get() = _locationViewLiveData

    private val currencyTypeLiveData: MutableLiveData<CurrencyType> = converterRepository.currencyType()

     fun getLocation(address: String) = viewModelScope.launch {

         Log.d(MainActivity.TAG, "getLocation: is called in vm ")

        _locationViewLiveData.postValue(Resource.loading(null))

        geocoderRepository.getLocation(address).let {

            if (it.isSuccessful) _locationViewLiveData.postValue(Resource.success(it.body()?.toLocationView()))
            else _locationViewLiveData.postValue(Resource.error(it.errorBody().toString(), null))


        }

    }
    fun getPropertyById(id: Int): Flow<Property> = propertyRepository.getPropertyById(id)

    fun updateProperty(property: Property) = viewModelScope.launch(Dispatchers.IO) {
        propertyRepository.updateProperty(property)
    }

    fun getPoiChipGroup(property: Property, context: Context?): ChipGroup {
        val chipGroup = ChipGroup(context)
        for(poi in property.poiList) {
            val currentChip = Chip(context)
            currentChip.text = poi
            chipGroup.addView(currentChip)
        }
        return chipGroup
    }

     fun currencyType(): MutableLiveData<CurrencyType>{
        return currencyTypeLiveData
    }

    fun getStaticMapUrl(currentProperty: Property): String {

        val currentLatLng = "${currentProperty.lat},${currentProperty.lng}"


        return "${Constants.BASE_URL_STATIC_MAP}&center=$currentLatLng&${Constants.DEFAULT_ZOOM_AND_SIZE}&${Constants.DEFAULT_MARKER_TYPE}$currentLatLng&key=${BuildConfig.MAPS_API_KEY}"

    }

    fun createAddress(property: Property): String {

        return "${property.address},${property.postalCode},${property.country}"

    }
}