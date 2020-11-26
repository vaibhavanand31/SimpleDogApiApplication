package com.example.simpledogapiapplication

import android.content.Context
import androidx.room.Room
import com.example.simpledogapiapplication.database.DogDatabase
import com.example.simpledogapiapplication.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ServiceLocator(applicationContext: Context) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    val database = Room.databaseBuilder(
        applicationContext,
        DogDatabase::class.java,
        "dog_database"
    ).fallbackToDestructiveMigration().build()
}