package com.example.masterdetailflowkotlintest.ui.detail

import android.content.Context
import androidx.lifecycle.*
import com.example.masterdetailflowkotlintest.model.Property
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PropertyDetailViewModel @Inject constructor(

    var propertyRepository: PropertyRepository

) : ViewModel() {


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

}