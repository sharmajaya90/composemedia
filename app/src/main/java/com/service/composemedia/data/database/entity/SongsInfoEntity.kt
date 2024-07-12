package com.service.composemedia.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_songsinfo")
class SongsInfoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name = "songs_id")
    var mediaId: String? = null
    @ColumnInfo(name = "songs_title")
    var title: String? = null
    @ColumnInfo(name = "songs_subtitle")
    var subtitle: String? = null
    @ColumnInfo(name = "songs_url")
    var songUrl: String? = null
    @ColumnInfo(name = "songs_image_url")
    var imageUrl: String? = null
}
