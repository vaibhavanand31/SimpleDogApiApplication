package com.example.simpledogapiapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalDogInfo::class], version = 2)
abstract class DogDatabase: RoomDatabase() {
    abstract fun getDogDao(): DogDao
}