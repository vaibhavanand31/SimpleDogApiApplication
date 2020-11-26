package com.example.simpledogapiapplication.data.models

import com.example.simpledogapiapplication.database.LocalDogInfo
import com.example.simpledogapiapplication.network.models.WebDogInfo
import java.io.Serializable
import java.util.*

data class Breed (
    val id: Int,
    val name: String,
    val subBreed: String = "",
    val isFaved: Boolean
) : Comparable<WebDogInfo>, Serializable {
    val title: String
        get() = if (subBreed != "") "${subBreed.capitalize(Locale.CANADA)} ${
            name.capitalize(
                Locale.CANADA
            )
        }" else "${name.capitalize(Locale.CANADA)}"

    override fun compareTo(other: WebDogInfo): Int {
        val compareBreed = name.compareTo(other.name)
        return if (compareBreed != 0) compareBreed else title.compareTo((other.title))
    }

    fun toLocalDogInfo(): LocalDogInfo = LocalDogInfo(id, name, subBreed, isFaved)
}