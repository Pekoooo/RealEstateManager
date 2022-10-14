package com.example.masterdetailflowkotlintest

import org.junit.Test
import java.net.URLEncoder

class EncodeUrlTest {

    companion object {
        const val EXPECTED_URL = "http://stackoverflow.com/search?q=37+all%C3%A9e+de+la+venerie"
    }

    @Test
    fun queryIsCorrect(){
        val query: String = URLEncoder.encode("37 allée de la venerie", Charsets.UTF_8.name())
        val url = "http://stackoverflow.com/search?q=$query"

        assert(url == EXPECTED_URL)
    }
}