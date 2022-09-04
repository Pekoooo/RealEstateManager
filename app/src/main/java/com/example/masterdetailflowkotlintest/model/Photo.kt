package com.example.masterdetailflowkotlintest.model

data class Photo(
    val id: Int,
    val path: String,
    var isMain: Boolean
){

    var description: String? = null

}
