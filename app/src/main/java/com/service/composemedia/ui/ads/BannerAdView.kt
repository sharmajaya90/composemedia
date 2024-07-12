package com.service.composemedia.ui.ads

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun BannerAdView(adMobBannerId:String?="ca-app-pub-4241681549957796/5652500824") {
    val context = LocalContext.current
    val adMobBanner: AdView = if (!adMobBannerId.isNullOrEmpty()) {
        val banner = AdView(context)
        banner.setAdSize(AdSize.BANNER)
        banner.adUnitId = adMobBannerId
        banner
    } else return


    AndroidView(
        factory = { context ->
            adMobBanner.apply {
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        },
        modifier = Modifier.fillMaxWidth()
    )

    adMobBanner.adListener = object : AdListener() {
        override fun onAdLoaded() {
            Toast.makeText(context,"onAdLoaded",Toast.LENGTH_SHORT).show()
        }

        override fun onAdFailedToLoad(loadError: LoadAdError) {
            super.onAdFailedToLoad(loadError)
            Toast.makeText(context,"onAdFailedToLoad",Toast.LENGTH_SHORT).show()
        }

        override fun onAdClosed() {
            Toast.makeText(context,"onAdClosed",Toast.LENGTH_SHORT).show()

        }

    }
}