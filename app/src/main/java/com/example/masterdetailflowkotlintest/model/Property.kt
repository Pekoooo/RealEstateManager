package com.example.masterdetailflowkotlintest.model

import android.os.Parcelable
import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.masterdetailflowkotlintest.utils.CurrencyConverter
import java.io.Serializable


@Entity(tableName = "property_table")
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "property_id")
    val id: Int = 0,
    @ColumnInfo(name = "property_surface")
    val surface: String,
    @ColumnInfo(name = "property_type")
    val type: String,
    @ColumnInfo(name = "property_address")
    val address: String,
    @ColumnInfo(name = "property_city")
    val city: String,
    @ColumnInfo(name = "property_neighborhood")
    val neighborhood: String,
    @ColumnInfo(name = "property_postalCode")
    val postalCode: String,
    @ColumnInfo(name = "property_country")
    val country: String,
    @ColumnInfo(name = "property_price")
    val price: String,
    @ColumnInfo(name = "property_bathrooms")
    val bathrooms: String,
    @ColumnInfo(name = "property_bedrooms")
    val bedrooms: String,
    @ColumnInfo(name = "property_listedAt")
    val listedAt: String,
    @ColumnInfo(name = "property_rooms")
    val rooms: String,
    @ColumnInfo(name = "property_description")
    val description: String,
    @ColumnInfo(name = "property_agent")
    val agent: String,
    @ColumnInfo(name = "property_main_picture")
    var mainPicture: String = "",
    @ColumnInfo(name = "property_picture_list")
    val pictureList: MutableList<Photo>,
    @ColumnInfo(name = "property_POI_list")
    val poiList: MutableList<String>,
    @ColumnInfo(name = "property_status")
    val isSold: Boolean = false
    ) : Serializable {

    val euroPrice: String
        get() {
            return CurrencyConverter.convertDollarToEuro(price.toInt())
        }


    override fun toString(): String = "$type in $neighborhood for $price"

    val toStringEuroPrice: String
        get() {
            return "$type in $neighborhood for $euroPrice"
        }

}


