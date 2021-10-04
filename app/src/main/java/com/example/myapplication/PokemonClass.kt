package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun main(args: Array<String>) {

    val number1 = Scanner(System.`in`)
    print("Enter Limit: ")

    var enteredLimit: Int = number1.nextInt()

    val number2 = Scanner(System.`in`)
    print("Enter offset: ")

    var enteredOffset: Int = number2.nextInt()

    val startTimeInMilliSeconds = System.currentTimeMillis()

    val url = URL("https://pokeapi.co/api/v2/pokemon/?limit=$enteredLimit&offset=$enteredOffset")

    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "GET"
        inputStream.bufferedReader().use {
            var height = 0
            var weight = 0
            val gson = Gson()
            val listPokemon = mutableListOf<PokemonData>()
            it.lines().forEach { line ->
                val pokemonResponse: PokemonResponse =
                    gson.fromJson(line, PokemonResponse::class.java)
                pokemonResponse.results.forEach {
                    val urlForPokemonDetails = URL(it.url)
                    with(urlForPokemonDetails.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"
                        inputStream.bufferedReader().use {
                            it.lines().forEach { line2 ->
                                val pokemonData: PokemonData =
                                    gson.fromJson(line2, PokemonData::class.java)
                                height += pokemonData.height
                                weight += pokemonData.weight
                                listPokemon.add(pokemonData)
                            }
                        }
                    }
                }
            }
            println("Average Height" + (height / listPokemon.size))
            println("Average Weight" + (weight / listPokemon.size))

            println("Total time in Seconds :" + getTimeInSeconds(startTimeInMilliSeconds))
        }
    }
}

fun getTimeInSeconds(startTimeInMilliSeconds: Long): Long {
    return ((System.currentTimeMillis() - startTimeInMilliSeconds) / 1000)
}
