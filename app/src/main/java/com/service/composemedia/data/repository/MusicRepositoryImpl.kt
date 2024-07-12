package com.service.composemedia.data.repository

import com.service.composemedia.data.dto.SongDto
import com.service.composemedia.data.mapper.toSong
import com.service.composemedia.data.remotedatabase.MusicRemoteDatabase
import com.service.composemedia.domain.repository.MusicRepository
import com.service.composemedia.other.Resource
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicRemoteDatabase: MusicRemoteDatabase,
) :
    MusicRepository {
    override fun getSongs() =
        flow {
            val songs = musicRemoteDatabase.getAllSongs().await().toObjects<SongDto>()

            if (songs.isNotEmpty()) {
                emit(Resource.Success(songs.map { it.toSong() }))
            }

        }

}