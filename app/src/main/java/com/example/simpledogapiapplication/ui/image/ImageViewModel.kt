package com.example.simpledogapiapplication.ui.image

import androidx.lifecycle.*
import com.example.simpledogapiapplication.data.ImageRepository
import com.example.simpledogapiapplication.data.models.Breed
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: ImageRepository, private val breed: Breed): ViewModel() {

    val dogImageUrl = repository.dogImage

    private val _shareDogImage = MutableLiveData<String>()
    val shareDogImage: LiveData<String> get() = _shareDogImage

    private val _browseDogImage = MutableLiveData<String>()
    val browseDogImage: LiveData<String> get() = _browseDogImage

    init {
        viewModelScope.launch {
            repository.getImage(breed)
        }
    }

    fun share(){
        dogImageUrl.value?.let {
            _shareDogImage.value = it
        }
    }

    fun onShareComplete() {
        _shareDogImage.value = null
    }

    fun openInBrowser() {
        dogImageUrl.value?.let {
            _browseDogImage.value = it
        }
    }

    fun onBrowseComplete() {
        _browseDogImage.value = null
    }
}

class ImageViewModelFactory(private val repository: ImageRepository, private val breed: Breed): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(repository, breed) as T
        }
        throw IllegalArgumentException("Not supported")
    }
}