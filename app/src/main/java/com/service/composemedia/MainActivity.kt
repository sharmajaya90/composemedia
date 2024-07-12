package com.service.composemedia

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.os.BuildCompat
import com.service.composemedia.data.service.MusicService
import com.service.composemedia.ui.MusicPlayerApp
import com.service.composemedia.ui.theme.MusicPlayerTheme
import com.service.composemedia.ui.viewmodels.SharedViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.service.composemedia.ui.ads.updateAdMobApplicationId
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.android.BuildConfig

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //update adMobId
        //updateAdMobApplicationId(BuildConfig.LIBRARY_PACKAGE_NAME)
        setContent {
            val systemUiController = rememberSystemUiController()

            val useDarkIcons = !isSystemInDarkTheme()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }
            MusicPlayerTheme {
                MusicPlayerApp(
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.destroyMediaController()
        stopService(Intent(this, MusicService::class.java))
    }
}


