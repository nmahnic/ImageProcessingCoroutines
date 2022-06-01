package com.nicomahnic.imageprocessingcoroutines

import android.graphics.Bitmap

import retrofit2.http.GET


interface Api {

    @GET("animals.json")
    suspend fun getAnimals() : String

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/CatalinStefan/animalApi/master/"
    }
}