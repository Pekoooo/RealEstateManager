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

    val allProperties: LiveData<List<Property>> = propertyRepository.allProperties.asLiveData()

    private val _filteredList: MutableLiveData<List<Property>> = MutableLiveData()
    val filteredList: LiveData<List<Property>> get() = _filteredList

    fun searchUserCriteria(
        type: String?,
        city: String?,
        neighbourhood: String?,
        soldLast3Month: Boolean,
        addedLess7Days: Boolean,
        startingPrice: Int?,
        priceLimit: Int?,
        sizeFrom: Int?,
        sizeUpTo: Int?,
        numberOfPhoto: Boolean
    ){
        searchQuery(
            buildTypeList(type),
            buildCityList(city),
            buildNeighbourhoodList(neighbourhood),
            buildMinSurface(sizeFrom),
            buildMaxSurface(sizeUpTo),
            buildNumberOfPhoto(numberOfPhoto),
            buildMinPrice(startingPrice),
            buildMaxPrice(priceLimit),
            soldLast3Month,
            addedLess7Days
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
        isNearMinSurface: Int,
        isNearMaxSurface: Int,
        isNearNumberOfPhotos: Int,
        isNearMinPrice: Int,
        isNearMaxPrice: Int,
        isNearSaleStatus: Boolean,
        isAddedLastSevenDays: Boolean
    ) {
        val temp: MutableList<Property> = mutableListOf()

        viewModelScope.launch {
            temp.addAll(
                propertyRepository.getPropertyResearch(
                    isNearTypeProperty,
                    isNearCity,
                    isNearNeighbourhood,
                    isNearMinSurface,
                    isNearMaxSurface,
                    isNearNumberOfPhotos,
                    isNearMinPrice,
                    isNearMaxPrice,
                    isNearSaleStatus,
                ).first()
            )

            _filteredList.value = temp
        }
    }



}