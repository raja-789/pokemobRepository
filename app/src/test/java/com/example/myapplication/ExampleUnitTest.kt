package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun getPokemonList () {
        // call the api
        val url = URL("https://pokeapi.co/api/v2/pokemon/?limit=$10&offset=$10")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            inputStream.bufferedReader().use {
                // verify the response is OK
                assertEquals(200, responseCode)
            }
        }
    }
}