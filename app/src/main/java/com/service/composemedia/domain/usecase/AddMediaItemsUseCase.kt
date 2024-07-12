package com.service.composemedia.domain.usecase

import com.service.composemedia.domain.model.Song
import com.service.composemedia.domain.service.MusicController
import javax.inject.Inject

class AddMediaItemsUseCase @Inject constructor(private val musicController: MusicController) {

    operator fun invoke(songs: List<Song>) {
        musicController.addMediaItems(songs)
    }
}