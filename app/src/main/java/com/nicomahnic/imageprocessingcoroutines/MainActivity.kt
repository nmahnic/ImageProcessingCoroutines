package com.nicomahnic.imageprocessingcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val IMAGE_URL = "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener {
            coroutineScope.launch {
                val originalDataDeferred = coroutineScope.async(Dispatchers.IO) {
                    RetrofitClient.service.getAnimals()
                }
                val originalData = originalDataDeferred.await()
                println(originalData)
//                loadImage(originalBitmap)

//                val originalDeferred = coroutineScope.async(Dispatchers.IO) { getOriginalBitmap() }
//                val originalBitmap = originalDeferred.await()
                val originalBitmap = withContext(coroutineScope.coroutineContext + Dispatchers.IO) { getOriginalBitmap() }
                loadImage(originalBitmap)

                val filteredDeferred = coroutineScope.async(Dispatchers.Default) { applyFilter(originalBitmap) }
                delay(1000L)
                val filteredBitmap = filteredDeferred.await()
                loadImage(filteredBitmap)

            }
        }

    }

    private fun getBitmapByGlide(){
        Glide.with(this)
            .load(IMAGE_URL)
            .centerCrop()
            .into(imageView)
        imageView.visibility = View.VISIBLE
    }

    private fun getOriginalBitmap() =
        URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }

    private fun applyFilter(originalBitmap: Bitmap) = Filter.apply(originalBitmap)

    private fun loadImage(bmp: Bitmap) {
        progressBar.visibility = View.GONE
        imageView.setImageBitmap(bmp)
        imageView.visibility = View.VISIBLE
    }
}
