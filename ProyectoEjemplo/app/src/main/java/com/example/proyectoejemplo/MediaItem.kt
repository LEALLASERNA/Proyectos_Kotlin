package com.example.proyectoejemplo

import android.annotation.SuppressLint

data class MediaItem(
    val id: Int,
    val title: String,
    val thumb: String,
    val type: Type
) {
    enum class Type{ PHOTO, VIDEO }
}

fun getMedia() = (1..9).map {
    MediaItem(
        id = it,
        title = "Title $it",
        thumb = "https://world.digimoncard.com/images/cardlist/card/EX1-00$it.png", // URL dinámica
        type = if (it % 3 == 0) MediaItem.Type.VIDEO else MediaItem.Type.PHOTO
    )
}