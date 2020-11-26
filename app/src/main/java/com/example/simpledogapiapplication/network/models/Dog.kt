package com.example.simpledogapiapplication.network.models

import android.util.Log
import com.example.simpledogapiapplication.database.LocalDogInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@JsonClass(generateAdapter = true)
data class DogListWrapper(
    val message: Map<String, List<String>>
){
    fun getBreeds():List<WebDogInfo> {
        val result = ArrayList<WebDogInfo>()
        for (data in message){
            if(!data.value.isEmpty()){
                for (subBreed in data.value) {
                    result.add(WebDogInfo(data.key, subBreed))
                }
            }
            else {
                result.add(WebDogInfo(data.key))
            }
        }
        result.sort()
        return result
    }
}

@JsonClass(generateAdapter = true)
data class WebDogInfo(
    val name: String,
    val subBreed: String = ""
) : Comparable<WebDogInfo>, Serializable {
    val title: String get() = if (subBreed!= "" ) "${subBreed.capitalize(Locale.CANADA)} ${name.capitalize(Locale.CANADA)}" else "${name.capitalize(Locale.CANADA)}"

    override fun compareTo(other: WebDogInfo): Int {
        val compareBreed = name.compareTo(other.name)
        return if (compareBreed != 0) compareBreed else title.compareTo((other.title))

    }

    fun toLocalDogInfo(): LocalDogInfo {
        return LocalDogInfo(name = name, subBreed = subBreed)
    }
}
