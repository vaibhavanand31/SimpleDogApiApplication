package com.example.simpledogapiapplication.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogImage(
    val message: String
){
    val imageUrl: String get() = message
}