package com.example.masterdetailflowkotlintest.placeholder

import com.example.masterdetailflowkotlintest.model.pojo.Photo
import com.example.masterdetailflowkotlintest.model.pojo.Property
import java.util.ArrayList
import java.util.HashMap


object PlaceholderContent {

    private val POI: MutableList<String> = ArrayList()
    private val PROPERTY_MAIN_PICTURE_LIST: MutableList<String> = ArrayList()
    private val PROPERTY_TYPE_LIST: MutableList<String> = ArrayList()
    private val PROPERTY_ADDRESS_LIST: MutableList<String> = ArrayList()
    private val PROPERTY_CITY_LIST: MutableList<String> = ArrayList()
    private val PROPERTY_LISTED_DATES: MutableList<String> = ArrayList()
    private val PROPERTY_INTERIOR_PHOTOS: MutableList<String> = ArrayList()
    private val PROPERTY_NEIGHBORHOOD_LIST: MutableList<String> = ArrayList()
    private val PROPERTY_DESCRIPTION: MutableList<String> = ArrayList()
    val ITEMS: MutableList<Property> = ArrayList()
    val ITEM_MAP: MutableMap<Int, Property> = HashMap()

    init {
        createPOI()
        addTypeList()
        addAddressesList()
        addCityList()
        addListedDates()
        addMainPictures()
        addInteriorPhotos()
        addNeighborhoods()
        addDescriptions()
        //addProperties()
    }

    private fun addProperties() {
        var x = 0
        while (x < 15) {
            addProperty(generateProperty())
            x++
        }
    }

    private fun addProperty(item: Property) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun addDescriptions() {
        PROPERTY_DESCRIPTION.add("If you love breathtaking views then this is the home for you! This 2 bedroom 2 bathroom penthouse suite offers one of the city\\'s most impressive outlooks spanning across downtown, the harbour and North Shore mountains. This home has been tastefully renovated throughout including flooring, both bathrooms and the kitchen. The bright open kitchen overlooks the spacious dining and living area with floor to ceiling windows, gas fireplace and access to a large balcony perfect for dining al fresco. Paris Place is a rental friendly building with amazing amenities like the fitness centre, full size indoor pool, sauna/steam room, hot tub, and common garden area. Just steps from some of Vancouver's finest restaurants &amp; shopping, elementary school, parks and transit. Excellent investment opportunity!")
        PROPERTY_DESCRIPTION.add("KITS GEM in the best pocket North of 4th w/beach vibes!!! RENOVATED, South facing 709sf one bedroom with patio and GARDEN make it like a TOWNHOUSE. Large flexible layout perfect for those needing to work from home or wanting to add a nursery/den. Updates include new high gloss white kitchen, wide plank white oak flooring, new matte black fixtures, new lighting, NEST control and a jacuzzi tub. Dreamy built in cabinetry for extras. South facing covered patio spills over into the garden for you to relax and enjoy year-round. Short walk to future Arbutus Strain, shopping, beach and the Greenway. Parking and storage included. Pets and rentals allowed w/ restrictions. Well managed &amp; beautifully landscaped building. Don\\'t miss out!")
        PROPERTY_DESCRIPTION.add("Ultima by Adara Development, located in UBC\\'s popular Wesbrook Village. Garden level home with tree lined patio looking into courtyard and infinity ponds. Quartz stone countertop, stainless steel appliances including stove, microwave, dish washer and double door fridge with water/ice dispenser. In–suite laundry, spacious open concept living area, 1 parking + 1 storage locker. Conveniently located close to UBC campus, U–Hill schools, Save–on Foods, transit, dining &amp; shopping. Open House 2–4 Sat August 8 + 2:30–4:30 pm Sun August 7.")
    }

    private fun addNeighborhoods() {
        PROPERTY_NEIGHBORHOOD_LIST.add("Bronx")
        PROPERTY_NEIGHBORHOOD_LIST.add("Brooklyn")
        PROPERTY_NEIGHBORHOOD_LIST.add("Manhattan")
        PROPERTY_NEIGHBORHOOD_LIST.add("Staten Island")
    }


    private fun addTypeList() {
        PROPERTY_TYPE_LIST.add("Flat")
        PROPERTY_TYPE_LIST.add("House")
        PROPERTY_TYPE_LIST.add("Penthouse")
    }

    private fun addMainPictures() {
        PROPERTY_MAIN_PICTURE_LIST.add("https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-600w-377330812.jpg")
        PROPERTY_MAIN_PICTURE_LIST.add("https://image.shutterstock.com/image-photo/buckley-wa-usa-sept-20-600w-1185966328.jpg")
        PROPERTY_MAIN_PICTURE_LIST.add("https://image.shutterstock.com/image-photo/europe-modern-complex-residential-apartment-600w-1434571649.jpg")
    }

    private fun addAddressesList() {
        PROPERTY_ADDRESS_LIST.add("37 allée de la venerie")
        PROPERTY_ADDRESS_LIST.add("3726 Allison Avenue")
        PROPERTY_ADDRESS_LIST.add("3624 Zappia Drive")
        PROPERTY_ADDRESS_LIST.add("3595 Musgrave Street")
        PROPERTY_ADDRESS_LIST.add("3832 Brownton Road")
        PROPERTY_ADDRESS_LIST.add("3301 Fowler Avenue")
        PROPERTY_ADDRESS_LIST.add("3301 Fowler Avenue")
    }

    private fun addCityList() {
        PROPERTY_CITY_LIST.add("Texas")
        PROPERTY_CITY_LIST.add("Missouri")
        PROPERTY_CITY_LIST.add("Wisconsin")
        PROPERTY_CITY_LIST.add("New York")
        PROPERTY_CITY_LIST.add("Michigan")
        PROPERTY_CITY_LIST.add("New Jersey")
        PROPERTY_CITY_LIST.add("Colorado")
    }

    private fun addListedDates() {
        PROPERTY_LISTED_DATES.add("2022-08-19")
        PROPERTY_LISTED_DATES.add("2022-10-26")
        PROPERTY_LISTED_DATES.add("2022-11-08")
        PROPERTY_LISTED_DATES.add("2022-11-09")
        PROPERTY_LISTED_DATES.add("2022-12-01")
    }

    private fun addInteriorPhotos() {
        PROPERTY_INTERIOR_PHOTOS.add("https://image.shutterstock.com/image-illustration/modern-bright-minimalist-bedroom-orange-600w-1927563686.jpg")
        PROPERTY_INTERIOR_PHOTOS.add("https://image.shutterstock.com/image-photo/kitchen-new-luxury-home-quartz-600w-1818540647.jpg")
        PROPERTY_INTERIOR_PHOTOS.add("https://image.shutterstock.com/image-photo/chicago-il-usa-september-9-600w-1915460233.jpg")
        PROPERTY_INTERIOR_PHOTOS.add("https://image.shutterstock.com/image-illustration/interior-bedroom-wall-mockup-3d-600w-1920245540.jpg")
    }

     private fun generateProperty(): Property {
        return Property(
            (0..20).random(),
            (0..200).random().toString() + " sqm",
            PROPERTY_TYPE_LIST.random(),
            PROPERTY_ADDRESS_LIST.random(),
            PROPERTY_CITY_LIST.random(),
            PROPERTY_NEIGHBORHOOD_LIST.random(),
            "78310",
            "USA",
            (0..2000000).random().toString() + " $",
            (1..5).random().toString(),
            (1..5).random().toString(),
            PROPERTY_LISTED_DATES.random(),
            (1..5).random().toString(),
            PROPERTY_MAIN_PICTURE_LIST.random(),
            "Agent Smith",
            "main picture",
            arrayListOf(
                Photo(
                    "path",
                    false
                )
            ),
            arrayListOf("poi")
        )
    }

    private fun createPOI() {
        POI.add("School")
        POI.add("Theatre")
        POI.add("Park")
        POI.add("Train station")
        POI.add("High school")
    }
}