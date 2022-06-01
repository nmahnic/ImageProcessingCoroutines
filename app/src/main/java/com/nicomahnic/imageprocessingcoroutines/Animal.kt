package com.nicomahnic.imageprocessingcoroutines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Animal(
    val name: String,
    val location: String,
    val speed: Int,
    val diet: String,
    val lifespan: String,
    val image: String
) : Parcelable
