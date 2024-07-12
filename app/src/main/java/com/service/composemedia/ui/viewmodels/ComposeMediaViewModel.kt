package com.service.composemedia.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.service.composemedia.data.database.dao.SongsInfoDao
import com.service.composemedia.data.database.entity.SongsInfoEntity
import com.service.composemedia.data.mapper.toSong
import com.service.composemedia.ui.favorites.FavoriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class ComposeMediaViewModel @Inject constructor(
    private val retrofit: Retrofit, private val songsInfoDao: SongsInfoDao
) : ViewModel(){

    var favoriteUiState by mutableStateOf(FavoriteUiState())
    private var _favoriteSongsList = MutableStateFlow<List<SongsInfoEntity>>(emptyList())
    val favoriteSongsList: StateFlow<List<SongsInfoEntity>> get() = _favoriteSongsList
    private var _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite


    fun addSongsAsFavorite(songsInfoEntity: SongsInfoEntity){
        if (!checkIsFavorite(songsInfoEntity.mediaId?:"")) {
            _isFavorite.value = songsInfoDao.insertSongsInfo(songsInfoEntity) > 0
        }else{
            val value = songsInfoDao.delete(songsInfoEntity)
            Log.e("songsInfoDao.delete",":::$value")
            _isFavorite.value = value <0
        }
    }

    fun checkIsFavorite(songsId:String):Boolean{
        _isFavorite.value = songsInfoDao.provideIsSongsFavoriteById(songsId) == true
        return _isFavorite.value
    }

    fun provideAllFavoriteSongs(){
        viewModelScope.launch {
            favoriteUiState = favoriteUiState.copy(loading = true)
            //_favoriteSongsList.value = songsInfoDao.fetchAllFavoriteSongs()
            favoriteUiState = favoriteUiState.copy(loading = false,songs = _favoriteSongsList.value.map { it.toSong() })
        }
    }
}
