package com.service.composemedia.ui.ads

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.util.*

@SuppressLint("MissingPermission")
class CoreAdMobHandler(
    val context: AppCompatActivity,
    private val adContainer: FrameLayout,
    applicationId: String? = null,
    bannerId: String? = null,
    fullScreenAd: String? = null,
    rewardAdId: String? = null,
    isRewardAdEnabled: Boolean,
    var adMode: String = "LIVE" // LIVE for release and TEST for debug
) {
//    private val TAG = CoreAdMobHandler::class.java.simpleName

    private val adMobBannerId = if (adMode == "TEST") {
        "ca-app-pub-3940256099942544/6300978111"
    } else {
        bannerId
    }

    private val adMobInterstitial = if (adMode == "TEST") {
        "ca-app-pub-3940256099942544/1033173712"
    } else {
        fullScreenAd
    }

    private val adMobRewardAdId = if (adMode == "TEST") {
        "ca-app-pub-3940256099942544/5224354917"
    } else {
        rewardAdId
    }

    //"ca-app-pub-3940256099942544/522435491"

    companion object {
        var mRewardedAd: RewardedAd? = null
        var isRewardAdUnitAvailable: Boolean = false
    }

    var isPersonalisedAd = false
    private var adMobFullScreen: InterstitialAd? = null

    init {
        context.updateAdMobApplicationId(applicationId = applicationId)
        MobileAds.initialize(context) {
            val emulatorDeviceId = "2d33c6a88d6ad892"
            val realmeDeviceId = "f7c56ea31785a0a2"
            val testDeviceIds = listOf(emulatorDeviceId, realmeDeviceId)

            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
//                    .setTestDeviceIds(testDeviceIds)
                    .build()
            )
            if (isRewardAdEnabled && (adMobRewardAdId ?: "").trim().isNullOrEmpty()) {
//                logReport(TAG, "ad is available ")
                loadRewardedAd()
            } else {
//                logReport(TAG, "ad is not available ")
            }
//            fetchFullScreenAd()
        }

    }


    fun loadRewardedAd(showVideo: Boolean = false) {
        if (mRewardedAd == null) {
            val adRequest = AdRequest.Builder().build()

            adMobRewardAdId?.let { rewardAdId ->
                RewardedAd.load(
                    /* context = */ context,
                    /* adUnitId = */ rewardAdId,
                    /* adRequest = */ adRequest,
                    /* loadCallback = */ object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
        //                        logReport(TAG, adError.message)
                            mRewardedAd = null
                            isRewardAdUnitAvailable = false
        //                        logReport(TAG, "onAdFailedToLoad: message -  " + adError.message)
                        }

                        override fun onAdLoaded(rewardedAd: RewardedAd) {
        //                        logReport(TAG, "Ad was loaded.")
                            mRewardedAd = rewardedAd
                            isRewardAdUnitAvailable = true
                            if (showVideo) {
                                Handler().postDelayed({
                                    showRewardedVideo()
                                }, 3000)
                            }

                        }
                    }
                )
            }
        }
    }

    fun showRewardedVideo() {
        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
//                        logReport(TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mRewardedAd = null
                        loadRewardedAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                        logReport(TAG, "Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mRewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
//                        logReport(TAG, "Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }

            mRewardedAd?.show(context, OnUserEarnedRewardListener() {
                fun onUserEarnedReward(rewardItem: RewardItem) {
                    var rewardAmount = rewardItem.amount
//                    logReport("TAG", "User earned the reward.")
                }
            })
        }
    }

    private fun removeBannerView() {
        context.runOnUiThread {
            adContainer.removeAllViews()
            adContainer.visibility = View.GONE
        }
    }


    private fun fetchFullScreenAd() {
        if (adMobInterstitial.isNullOrEmpty()) {
            adMobInterstitial?.let {
                InterstitialAd.load(
                    /* context = */ context,
                    /* adUnitId = */ it,
                    /* adRequest = */ getAdRequest(),
                    /* loadCallback = */ object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.e("fetchFullScreenAd", "adError > ${adError.responseInfo?.toString()} ")
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            adMobFullScreen = interstitialAd
                            adMobFullScreen?.fullScreenContentCallback = object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent()
                                    fetchFullScreenAd()
                                }

                                override fun onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent()
                                    adMobFullScreen = null
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    private fun getAdRequest(): AdRequest {
        return  AdRequest.Builder().build()
    }

    @Composable
    fun loadAdBanner() {
        val context = LocalContext.current
        val adMobBanner: AdView = if (!adMobBannerId.isNullOrEmpty()) {
            adContainer.removeAllViews()
            val banner = AdView(context)
            banner.setAdSize(AdSize.BANNER)
            banner.adUnitId = adMobBannerId
            banner
        } else return

        AndroidView(
            factory = { context ->
                adMobBanner.apply {
                    adUnitId = "ca-app-pub-4241681549957796~8958488308"
                    loadAd(AdRequest.Builder().build())
                }
            },
            update = { adView ->
                adView.loadAd(AdRequest.Builder().build())
            }
        )

        adMobBanner.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adContainer.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(loadError: LoadAdError) {
                super.onAdFailedToLoad(loadError)
                adContainer.visibility = View.GONE
            }

            override fun onAdClosed() {
                adContainer.visibility = View.GONE
            }

        }
    }

}