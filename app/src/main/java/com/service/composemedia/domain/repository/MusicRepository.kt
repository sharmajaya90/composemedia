package com.service.composemedia.domain.repository

import com.service.composemedia.domain.model.Song
import com.service.composemedia.other.Resource
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getSongs(): Flow<Resource<List<Song>>>
}