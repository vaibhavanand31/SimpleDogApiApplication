package com.example.simpledogapiapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DogDao {

    @Query("SELECT * FROM dog_infos")
    fun getPokemonInfos(): LiveData<List<LocalDogInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(dogInfos: List<LocalDogInfo>)

    @Update
    fun update(breed: LocalDogInfo)

}