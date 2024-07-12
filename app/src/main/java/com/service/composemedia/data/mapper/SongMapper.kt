package com.service.composemedia.data.mapper

import androidx.media3.common.MediaItem
import com.service.composemedia.data.database.entity.SongsInfoEntity
import com.service.composemedia.data.dto.SongDto
import com.service.composemedia.domain.model.Song

fun SongDto.toSong() =
    Song(
        mediaId = mediaId,
        title = title,
        subtitle = subtitle,
        songUrl = songUrl,
        imageUrl = imageUrl
    )
fun SongsInfoEntity.toSong() =
    Song(
        mediaId = mediaId?:"",
        title = title?:"",
        subtitle = subtitle?:"",
        songUrl = songUrl?:"",
        imageUrl = imageUrl?:""
    )

fun MediaItem.toSong() =
    Song(
        mediaId = mediaId,
        title = mediaMetadata.title.toString(),
        subtitle = mediaMetadata.subtitle.toString(),
        songUrl = mediaId,
        imageUrl = mediaMetadata.artworkUri.toString()
    )