package com.example.masterdetailflowkotlintest.ui.detail


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterdetailflowkotlintest.BuildConfig
import com.example.masterdetailflowkotlintest.enums.CurrencyType
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.repositories.ConverterRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.utils.Constants
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
    private var converterRepository: ConverterRepository,




) : ViewModel() {


    private val currencyTypeLiveData: MutableLiveData<CurrencyType> = converterRepository.currencyType()

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

    fun switchCurrencyUi(){
        converterRepository.switchCurrency()
    }
}