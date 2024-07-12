package com.service.composemedia.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.service.composemedia.data.database.entity.SongsInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SongsInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongsInfo(entity: SongsInfoEntity): Long
    @Update
    fun updateSongsInfo(entity: SongsInfoEntity): Int
    @Delete
    fun delete(entity: SongsInfoEntity): Int

    @Query("SELECT * FROM table_songsinfo")
    fun fetchAllRegisterSongs(): Flow<List<SongsInfoEntity>>


    @Query("SELECT * FROM table_songsinfo")
    fun fetchAllFavoriteSongs(): Flow<List<SongsInfoEntity>>
    /*
    @Query("DELETE FROM table_songsinfo WHERE id = :id")
    fun deleteSongsInfoWithId(id: Long): Int
*/
    @Query("SELECT EXISTS(SELECT * FROM table_songsinfo WHERE songs_id = :songs_id)")
    fun provideIsSongsFavoriteById(songs_id: String): Boolean?
}