package com.example.masterdetailflowkotlintest.model

import java.io.Serializable

data class Property(val id: Long,
                    val surface: String,
                    val type: String,
                    val address: String,
                    val city: String,
                    val neighborhood: String,
                    val postalCode: String,
                    val country: String,
                    val price: String,
                    val bathrooms: String,
                    val bedrooms: String,
                    val listedAt: String,
                    val rooms: String,
                    val poi: MutableList<String>,
                    val mainPhoto: String,
                    val interiorPhotos: MutableList<String>,
                    val description: String
                     ) : Serializable {

    override fun toString(): String = "$type in $neighborhood for $price"
}


