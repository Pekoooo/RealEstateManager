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
                Utils.todayDate,
                "8",
                "Your typical childhood house",
                "Tristan",
                "https://i.imgur.com/PkQIrJx.jpg",
                getPictureList(),
                getPoiList(),
                false,
                48.75320809022753,
                1.9166888529994939

            )

        }

        val samplePropertyList = mutableListOf(
            Property(
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
                Utils.todayDate,
                "8",
                "Your typical childhood house",
                "Tristan",
                "https://i.imgur.com/PkQIrJx.jpg",
                getPictureList(),
                getPoiList(),
                false,
                48.75320809022753,
                1.9166888529994939

            ),

            Property(
                2,
                250,
                "Flat",
                "8 Rue du sillon",
                "Coignières",
                "Yvelines",
                "78310",
                "France",
                460000,
                "2",
                "4",
                Utils.todayDate,
                "8",
                "Your typical bestie childhood house",
                "Tristan",
                "https://i.pinimg.com/originals/b2/7f/c8/b27fc8901f18fe6b39856aec9d44e13d.jpg",
                getPictureList(),
                getPoiList(),
                false,
                48.75376613766151,
                1.9210808706006441

            ),

            Property(
                3,
                350,
                "Penthouse",
                "2321 Hedges road",
                "Golden",
                "Blaeberry",
                "V0A 1H1",
                "Canada",
                1360000,
                "2",
                "4",
                Utils.todayDate,
                "8",
                "Your typical remote A frame",
                "Tristan",
                "https://media.architecturaldigest.com/photos/57f3d9e9f96256c62629bfd0/4:3/w_2299,h_1724,c_limit/_a-frame-07_mikikokikuyama.jpg",
                getPictureList(),
                getPoiList(),
                false,
                51.454583169540065,
                -116.98303352633894

            ),

            Property(
                4,
                450,
                "House",
                "3528 West 14th ave",
                "Vancouver",
                "Kits",
                " V6R 2W4",
                "Canada",
                2360000,
                "2",
                "4",
                Utils.todayDate,
                "8",
                "Your typical childhood house",
                "Tristan",
                "https://livekitsilano.com/wp-content/uploads/sites/4/2020/03/3668-west-3rd-kitsilano-triplex.jpg",
                getPictureList(),
                getPoiList(),
                false,
                49.2595976390376,
                -123.18266949209384

            ),

            Property(
                5,
                550,
                "House",
                "44 Higashiyama",
                "Niseko",
                "Higashiyama",
                "048-1521",
                "Japon",
                360000,
                "2",
                "4",
                Utils.todayDate,
                "8",
                "Your typical childhood house",
                "Tristan",
                "https://i.imgur.com/zzkMrr5.png",
                getPictureList(),
                getPoiList(),
                false,
                42.840265570599385,
                140.67371348161095

            ),

            Property(
                6,
                650,
                "Mansion",
                "700 W E St",
                "San Diego",
                "California",
                "92101",
                "États-Unis",
                360000,
                "2",
                "4",
                Utils.todayDate,
                "8",
                "Your typical childhood house",
                "Tristan",
                "https://i.imgur.com/PkQIrJx.jpg",
                getPictureList(),
                getPoiList(),
                false,
                32.71509333687513,
                -117.16944867061822

            )


        )

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