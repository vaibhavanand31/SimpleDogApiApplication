package com.example.simpledogapiapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.simpledogapiapplication.data.models.Breed
import com.example.simpledogapiapplication.database.DogDatabase
import com.example.simpledogapiapplication.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class DogRepository(
    private val apiService: ApiService,
    private val database: DogDatabase
) {

    val dogLists: LiveData<List<Breed>> = Transformations.map(database.getDogDao().getPokemonInfos()) { localDataList ->
        localDataList.map {
            localDogInfo ->
                localDogInfo.toDomainDogInfo()
        }
    }

    suspend fun getDogs() {
        try {
            val result = apiService.getDogs().getBreeds()
            withContext(Dispatchers.IO) {
                database.getDogDao().insertAll(result.map {
                    it.toLocalDogInfo()
                })
            }
            //adapter.breeds = result.getBreeds()
            //adapter.notifyDataSetChanged()
        } catch (e: HttpException) {
            //For Http request related failures
        } catch (e: IOException) {
            //For JSON conversion
        } catch (e: Exception) {
            //For all other exceptions
        }
    }

    suspend fun savePokemon(breed: Breed) = withContext(Dispatchers.IO) {
        database.getDogDao().update(breed.toLocalDogInfo())
    }
}