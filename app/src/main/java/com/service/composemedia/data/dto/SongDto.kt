package com.service.composemedia.data.dto

data class SongDto(
    var mediaId: String = "",
    val title: String = "",
    val subtitle: String = "",
    val songUrl: String = "",
    val imageUrl: String = ""
)