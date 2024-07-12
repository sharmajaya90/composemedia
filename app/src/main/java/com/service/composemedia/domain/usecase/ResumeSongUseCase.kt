package com.service.composemedia.domain.usecase

import com.service.composemedia.domain.service.MusicController
import javax.inject.Inject

class ResumeSongUseCase @Inject constructor(private val musicController: MusicController) {
    operator fun invoke() = musicController.resume()
}