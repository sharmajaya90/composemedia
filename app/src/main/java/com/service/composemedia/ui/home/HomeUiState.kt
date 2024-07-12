package com.service.composemedia.ui.home

import com.service.composemedia.domain.model.Song

data class HomeUiState(
    val loading: Boolean? = false,
    val songs: List<Song>? = emptyList(),
    val selectedSong: Song? = null,
    val errorMessage: String? = null
)
