package com.example.simpledogapiapplication.ui.main

import android.content.Intent
import androidx.lifecycle.*
import com.example.simpledogapiapplication.data.DogRepository
import com.example.simpledogapiapplication.data.models.Breed
import com.example.simpledogapiapplication.ui.BundleKeys
import com.example.simpledogapiapplication.ui.image.ImageActivity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DogViewModel(private val dogRepository: DogRepository): ViewModel() {

    val dogLists = dogRepository.dogLists

    private val _navigateToImage = MutableLiveData<Breed?>(null)
    val navigatetoImage: LiveData<Breed?> get() = _navigateToImage

    fun onDogClick(breed: Breed) {
        _navigateToImage.value = breed
    }

    fun onNavigateToDetailsComplete() {
        _navigateToImage.value = null
    }

    fun onFaveClick(breed: Breed) {
        viewModelScope.launch {
            dogRepository.savePokemon(Breed(
               breed.id, breed.name, breed.subBreed, !breed.isFaved
            ))
        }
    }

    init {
        viewModelScope.launch {
            dogRepository.getDogs()
        }
    }
}

class DogViewModelFactory(private val dogRepository: DogRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(DogViewModel::class.java)){
           return DogViewModel(dogRepository) as T
       }
       throw IllegalArgumentException("Invalid model Class")
    }
}