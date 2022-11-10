package com.example.masterdetailflowkotlintest.utils

import com.example.masterdetailflowkotlintest.model.pojo.Photo
import com.example.masterdetailflowkotlintest.model.pojo.Property
import java.util.*

class DummyPropertyProvider {

    companion object {

        fun getDummyProperty(): Property {

            return Property(
                1,
                150,
                "House",
                "37 allée de la venerie",
                "Coignières",
                "Yvelines",
                "78310",
                "France",
                360000,
                "2",
                "4",
                Calendar.getInstance().time.toString(),
                "8",
                "Your typical childhood house",
                "Tristan",
                "https://i.imgur.com/PkQIrJx.jpg",
                getPictureList(),
                getPoiList(),
                false

            )

        }

        private fun getPoiList(): MutableList<String> {

            return mutableListOf(
                "School",
                "Daycare"
            )

        }

        private fun getPictureList(): MutableList<Photo> {

            return mutableListOf(
                Photo("https://i.imgur.com/XJ7fQrl.jpeg", false),
                Photo("https://i.imgur.com/g4pv8fd.jpeg", false)
            )

        }

    }


}