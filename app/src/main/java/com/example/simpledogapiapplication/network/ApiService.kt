package com.example.simpledogapiapplication.network

import com.example.simpledogapiapplication.network.models.DogImage
import com.example.simpledogapiapplication.network.models.DogListWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("breeds/list/all" )
    suspend fun getDogs(): DogListWrapper

    @GET("breed/{breed_name}/{sub_breed_name}/images/random")
    suspend fun getSubBreedImage(@Path("breed_name") breedName: String, @Path("sub_breed_name") subBreedName: String): DogImage

    @GET("breed/{breed_name}/images/random")
    suspend fun getBreedImage(@Path("breed_name") breedName: String): DogImage

}