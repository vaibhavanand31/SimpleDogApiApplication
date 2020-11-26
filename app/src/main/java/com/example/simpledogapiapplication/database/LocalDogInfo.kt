package com.example.simpledogapiapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simpledogapiapplication.data.models.Breed
import com.example.simpledogapiapplication.network.models.WebDogInfo

@Entity(tableName = "dog_infos")
data class LocalDogInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val subBreed: String,
    val isFaved: Boolean = false
) {
    companion object {
        fun fromWeb(webDogInfo: WebDogInfo): LocalDogInfo {
            return LocalDogInfo(
                name = webDogInfo.name,
                subBreed = webDogInfo.subBreed
            )
        }
    }

    fun toDomainDogInfo(): Breed {
        return Breed(
            id = id,
            name = name,
            subBreed = subBreed,
            isFaved = isFaved
        )
    }
}