package com.service.composemedia.ui.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.service.composemedia.data.database.dao.SongsInfoDao
import com.service.composemedia.data.mapper.toSong
import com.service.composemedia.domain.usecase.AddMediaItemsUseCase
import com.service.composemedia.domain.usecase.GetSongsUseCase
import com.service.composemedia.domain.usecase.PauseSongUseCase
import com.service.composemedia.domain.usecase.PlaySongUseCase
import com.service.composemedia.domain.usecase.ResumeSongUseCase
import com.service.composemedia.domain.usecase.SkipToNextSongUseCase
import com.service.composemedia.domain.usecase.SkipToPreviousSongUseCase
import com.service.composemedia.other.Resource
import com.service.composemedia.ui.home.HomeEvent
import com.service.composemedia.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val addMediaItemsUseCase: AddMediaItemsUseCase,
    private val playSongUseCase: PlaySongUseCase,
    private val pauseSongUseCase: PauseSongUseCase,
    private val resumeSongUseCase: ResumeSongUseCase,
    private val skipToNextSongUseCase: SkipToNextSongUseCase,
    private val skipToPreviousSongUseCase: SkipToPreviousSongUseCase,
    private val songsInfoDao: SongsInfoDao,
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.PlaySong -> playSong()

            HomeEvent.PauseSong -> pauseSong()

            HomeEvent.ResumeSong -> resumeSong()

            HomeEvent.FetchSong -> getSong()

            HomeEvent.FetchFavoriteSong -> getFavoriteSong()

            is HomeEvent.OnSongSelected -> homeUiState =
                homeUiState.copy(selectedSong = event.selectedSong)

            is HomeEvent.SkipToNextSong -> skipToNextSong()

            is HomeEvent.SkipToPreviousSong -> skipToPreviousSong()
        }
    }

    private fun getSong() {
        homeUiState = homeUiState.copy(loading = true)

        viewModelScope.launch {
            getSongsUseCase().catch {
                homeUiState = homeUiState.copy(
                    loading = false,
                    errorMessage = it.message
                )
            }.collect {
                homeUiState = when (it) {
                    is Resource.Success -> {
                        it.data?.let { songs ->
                            addMediaItemsUseCase(songs)
                        }

                        homeUiState.copy(
                            loading = false,
                            songs = it.data
                        )
                    }

                    is Resource.Loading -> {
                        homeUiState.copy(
                            loading = true,
                            errorMessage = null
                        )
                    }

                    is Resource.Error -> {
                        homeUiState.copy(
                            loading = false,
                            errorMessage = it.message
                        )
                    }
                }
            }
        }
    }


    private fun getFavoriteSong() {
        homeUiState = homeUiState.copy(loading = true)

       // _favoriteSongsList.value = songsInfoDao.fetchAllFavoriteSongs()
       // favoriteUiState = favoriteUiState.copy(loading = false,songs = _favoriteSongsList.value.map { it.toSong() })
        viewModelScope.launch {
            songsInfoDao.fetchAllFavoriteSongs().catch {
                homeUiState = homeUiState.copy(
                    loading = false,
                    errorMessage = it.message
                )
            }.collect {
                addMediaItemsUseCase(it.map { it.toSong() })
                homeUiState = homeUiState.copy(
                    loading = false,
                    songs = it.map { it.toSong() }
                )
            }
        }
    }

    private fun playSong() {
        homeUiState.apply {
            songs?.indexOf(selectedSong)?.let { song ->
                playSongUseCase(song)
            }
        }
    }

    private fun pauseSong() = pauseSongUseCase()

    private fun resumeSong() = resumeSongUseCase()

    private fun skipToNextSong() = skipToNextSongUseCase {
        homeUiState = homeUiState.copy(selectedSong = it)
    }

    private fun skipToPreviousSong() = skipToPreviousSongUseCase {
        homeUiState = homeUiState.copy(selectedSong = it)
    }
}
