package com.example.masterdetailflowkotlintest

import com.example.masterdetailflowkotlintest.model.Photo
import org.junit.Test

import org.junit.Assert.*


class AddPropertyTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }



        private val pictureList = mutableListOf<Photo>(
            Photo("path1", false),
            Photo("path2", true),
            Photo("path3", false),
            Photo("path4", false)
        )




    @Test
    fun updating_main_picture_with_success(){

        val currentPhoto = Photo("path4", false)
        currentPhoto.description = "Balcony"

        pictureList.filter { it.isMain }.forEach { it.isMain = false }
        pictureList.filter {it.path == currentPhoto.path}.forEach { it.isMain = true }
        pictureList.filter {it.path == currentPhoto.path}.forEach { it.description = currentPhoto.description }



        assert(pictureList[3].isMain)
        assert(pictureList[3].description == "Balcony")

    }


}