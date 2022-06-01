package com.nicomahnic.imageprocessingcoroutines

import android.graphics.Bitmap

import retrofit2.http.GET


interface Api {

    @GET("animal.json")
    suspend fun getAnimal() : Animal

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/nmahnic/ImageProcessingCoroutines/master/app/provider/"
    }
}