package com.example.masterdetailflowkotlintest.ui.filteredSearch

import androidx.lifecycle.*
import com.example.masterdetailflowkotlintest.model.pojo.Property
import com.example.masterdetailflowkotlintest.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilteredSearchViewModel @Inject constructor(
    val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _allProperties: MutableLiveData<List<Property>> = MutableLiveData()
    val allProperties: LiveData<List<Property>> get() = _allProperties

    private val _filteredList: MutableLiveData<List<Property>> = MutableLiveData()
    val filteredList: LiveData<List<Property>> get() = _filteredList

    fun getPropertyList() {
        viewModelScope.launch {
            _allProperties.value = propertyRepository.allProperties.first()
        }
    }

    fun searchUserCriteria(
        type: String?,
        city: String?,
        neighbourhood: String?,
        startingPrice: Int?,
        priceLimit: Int?,
        sizeFrom: Int?,
        sizeUpTo: Int?
    ){
        searchQuery(
            buildTypeList(type),
            buildCityList(city),
            buildNeighbourhoodList(neighbourhood),
            buildMinPrice(startingPrice),
            buildMaxPrice(priceLimit),
            buildMinSurface(sizeFrom),
            buildMaxSurface(sizeUpTo)
        )

    }

    private fun buildTypeList(type: String?): List<String> {
        val list: MutableList<String> = mutableListOf()

        if (type != null) {
            list.add(type)
        } else {
            val listP = allProperties.value
            for (item in listP!!) {
                if (!list.contains(item.type)) list.add(item.type)
            }
        }

        return list
    }

    private fun buildCityList(city: String?): List<String> {
        val list: MutableList<String> = mutableListOf()

        if (city != null) {
            list.add(city)
        } else {
            val listP = allProperties.value
            for (item in listP!!) {
                if (!list.contains(item.city)) list.add(item.city)
            }
        }

        return list
    }

    private fun buildNeighbourhoodList(neighbourhood: String?): List<String> {
        val list: MutableList<String> = mutableListOf()

        if (neighbourhood != null) {
            list.add(neighbourhood)
        } else {
            val listP = allProperties.value
            for (item in listP!!) {
                if (!list.contains(item.neighborhood)) list.add(item.neighborhood)
            }
        }

        return list
    }

    private fun buildMinSurface(sizeFrom: Int?) = sizeFrom ?: 0

    private fun buildMaxSurface(sizeUpTo: Int?) = sizeUpTo ?: 1000000

    private fun buildMinPrice(startingPrice: Int?) = startingPrice ?: 0

    private fun buildMaxPrice(maxPrice: Int?) = maxPrice ?: 100000000

    private fun buildSchoolList(school: Boolean): List<Boolean> {
        val list: MutableList<Boolean> = mutableListOf()

        if (school) {
            list.add(school)
        } else {
            list.add(true)
            list.add(false)
        }

        return list
    }

    private fun buildShopsList(school: Boolean): List<Boolean> {
        val list: MutableList<Boolean> = mutableListOf()

        if (school) {
            list.add(school)
        } else {
            list.add(true)
            list.add(false)
        }

        return list
    }

    private fun buildParkList(school: Boolean): List<Boolean> {
        val list: MutableList<Boolean> = mutableListOf()

        if (school) {
            list.add(school)
        } else {
            list.add(true)
            list.add(false)
        }

        return list
    }

    private fun buildNumberOfPhoto(numberOfPhoto: Boolean) = if (numberOfPhoto) 3 else 0

    private fun searchQuery(
        isNearTypeProperty: List<String>,
        isNearCity: List<String>,
        isNearNeighbourhood: List<String>,
        isNearMinPrice: Int,
        isNearMaxPrice: Int,
        isNearMinSurface: Int,
        isNearMaxSurface: Int,

    ) {
        val temp: MutableList<Property> = mutableListOf()

        viewModelScope.launch {
            temp.addAll(
                propertyRepository.getPropertyResearch(
                    isNearTypeProperty,
                    isNearCity,
                    isNearNeighbourhood,
                    isNearMinPrice,
                    isNearMaxPrice,
                    isNearMinSurface,
                    isNearMaxSurface

                ).first()
            )
            _filteredList.value = temp
        }
    }
}