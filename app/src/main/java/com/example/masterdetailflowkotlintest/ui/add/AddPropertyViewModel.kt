package com.example.masterdetailflowkotlintest.ui.add

import android.util.Log
import androidx.lifecycle.*
import com.example.masterdetailflowkotlintest.domain.SaveProperty
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.model.response.toLocationView
import com.example.masterdetailflowkotlintest.model.view.LocationView
import com.example.masterdetailflowkotlintest.repositories.GeocoderRepository
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import com.example.masterdetailflowkotlintest.ui.main.MainActivity
import com.example.masterdetailflowkotlintest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(

    private var propertyRepository: PropertyRepository,
    private var geocoderRepository: GeocoderRepository,
    private var saveProperty: SaveProperty

) : ViewModel() {

    val unitLiveData: MutableLiveData<Unit> = MutableLiveData()

    fun save(property: Property){
        viewModelScope.launch {

            saveProperty.save(property)
            unitLiveData.postValue(Unit)

        }
    }

    fun getPropertyById(id: Int): Flow<Property> = propertyRepository.getPropertyById(id)


}