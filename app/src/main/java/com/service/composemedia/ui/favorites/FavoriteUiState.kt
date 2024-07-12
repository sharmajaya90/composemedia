package com.service.composemedia.ui.favorites

import com.service.composemedia.domain.model.Song

data class FavoriteUiState(
    val loading: Boolean? = false,
    val songs: List<Song>? = emptyList(),
    val errorMessage: String? = null
)
