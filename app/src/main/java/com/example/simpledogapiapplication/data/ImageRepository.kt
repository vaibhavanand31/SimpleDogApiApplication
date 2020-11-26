package com.example.simpledogapiapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpledogapiapplication.data.models.Breed
import com.example.simpledogapiapplication.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class ImageRepository(private val apiService: ApiService) {
    private val _dogImage = MutableLiveData<String>()
    val dogImage: LiveData<String> get() = _dogImage

    suspend fun getImage(breed: Breed) {
        try {
            if (breed.subBreed != ""){
                _dogImage.value = apiService.getSubBreedImage(breed.name, breed.subBreed).imageUrl
            }
            else {
                _dogImage.value = apiService.getBreedImage(breed.name).imageUrl
            }
        } catch (e: HttpException) {
            //For Http request related failures
        } catch (e: IOException) {
            //For JSON conversion
        } catch (e: Exception) {
            //For all other exceptions
        }
    }
}