package com.example.masterdetailflowkotlintest.model.pojo

data class Photo(
    val path: String,
    var isMain: Boolean
){

    var description: String? = null

}
