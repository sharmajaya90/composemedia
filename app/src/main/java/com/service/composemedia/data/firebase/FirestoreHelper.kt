package com.service.composemedia.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.service.composemedia.data.dto.SongDto
import kotlinx.coroutines.tasks.await


object FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()
    private var songsUrlPath:String = "compose_media_songs"


    fun addSongs(song: SongDto, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection(songsUrlPath)
            .add(song)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    suspend fun getSongsList(): List<SongDto> {
        return try {
            val result = db.collection(songsUrlPath)
                .get()
                .await()
            result.documents.mapNotNull { data->
                val qaInfoData = data.toObject(SongDto::class.java)
                qaInfoData?.mediaId = data.id
                qaInfoData
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}