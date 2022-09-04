package com.example.masterdetailflowkotlintest.model

data class Photo(
    val id: Int,
    val path: String,
    val isMain: Boolean
){

    val description: String? = null

}
