package com.service.composemedia.domain.usecase

import com.service.composemedia.domain.repository.MusicRepository
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(private val musicRepository: MusicRepository) {
    operator fun invoke() = musicRepository.getSongs()
}