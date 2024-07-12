package com.service.composemedia.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.service.composemedia.ui.admin.PostSongScreen
import com.service.composemedia.ui.ads.BannerAdView
import com.service.composemedia.ui.favorites.FavoritesScreen
import com.service.composemedia.ui.home.HomeEvent
import com.service.composemedia.ui.home.HomeScreen
import com.service.composemedia.ui.home.HomeViewModel
import com.service.composemedia.ui.home.component.HomeBottomBar
import com.service.composemedia.ui.navigation.Destination
import com.service.composemedia.ui.options.DrawerContent
import com.service.composemedia.ui.settings.SettingsScreen
import com.service.composemedia.ui.songscreen.SongScreen
import com.service.composemedia.ui.songscreen.SongViewModel
import com.service.composemedia.ui.viewmodels.SharedViewModel

@Composable
fun MusicPlayerApp(sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()
    MusicPlayerNavHost(
        navController = navController,
        sharedViewModel = sharedViewModel
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicPlayerNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val musicControllerUiState = sharedViewModel.musicControllerUiState
    val activity = (LocalContext.current as ComponentActivity)

    NavHost(navController = navController, startDestination = Destination.home) {
        composable(route = Destination.home) {
            val mainViewModel: HomeViewModel = hiltViewModel()
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(key1 = Unit) {
                    mainViewModel.onEvent(HomeEvent.FetchSong)
                    isInitialized.value = true
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                    navController= navController
                )
                HomeBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onEvent = mainViewModel::onEvent,
                    song = musicControllerUiState.currentSong,
                    playerState = musicControllerUiState.playerState,
                    onBarClick = { navController.navigate(Destination.songScreen)
                    }
                )
            }
        }
        composable(route = Destination.songScreen) {
            val songViewModel: SongViewModel = hiltViewModel()
            SongScreen(
                onEvent = songViewModel::onEvent,
                musicControllerUiState = musicControllerUiState,
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = Destination.adSongScreen) {
            val songViewModel: SongViewModel = hiltViewModel()
            PostSongScreen(
                onEvent = songViewModel::onEvent,
                musicControllerUiState = musicControllerUiState,
                onNavigateUp = { navController.navigateUp() },
                navController = navController
            )
        }
        composable(route = Destination.settingsScreen) {
            val songViewModel: SongViewModel = hiltViewModel()
            SettingsScreen(
                onEvent = songViewModel::onEvent,
                musicControllerUiState = musicControllerUiState,
                onNavigateUp = { navController.navigateUp() },
                navController = navController
            )
        }
        composable(route = Destination.favoriteScreen) {
            val mainViewModel: HomeViewModel = hiltViewModel()
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(key1 = Unit) {
                    mainViewModel.onEvent(HomeEvent.FetchFavoriteSong)
                    isInitialized.value = true
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                FavoritesScreen(
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                    navController = navController
                )

                HomeBottomBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onEvent = mainViewModel::onEvent,
                    song = musicControllerUiState.currentSong,
                    playerState = musicControllerUiState.playerState,
                    onBarClick = {
                        navController.navigate(Destination.songScreen)
                    }
                )
            }
        }
        composable(route = Destination.optionScreen) {
            val songViewModel: SongViewModel = hiltViewModel()
            DrawerContent(
                onEvent = songViewModel::onEvent,
                musicControllerUiState = musicControllerUiState,
                onNavigateUp = { navController.navigateUp() },
                navController = navController
            )
        }
    }
}

