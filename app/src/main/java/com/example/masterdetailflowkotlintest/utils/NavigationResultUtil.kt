package com.example.masterdetailflowkotlintest.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.masterdetailflowkotlintest.model.appModel.Property

class NavigationResultUtil {

    fun Fragment.getNavigationResult(key: String = "result") =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Property>(key)

    fun Fragment.setNavigationResult(result: Property, key: String = "result") {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }


}