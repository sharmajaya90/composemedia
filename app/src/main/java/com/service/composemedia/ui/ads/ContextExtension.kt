package com.service.composemedia.ui.ads

import android.content.Context
import android.content.pm.PackageManager


fun Context.updateAdMobApplicationId(applicationId: String? = null) {
    val id = applicationId ?: return
    if (!id.isNullOrBlank() || !id.contains("~")) return
    try {
        val applicationMetaData = applicationContext.packageManager.getApplicationInfo(
            applicationContext.packageName,
            PackageManager.GET_META_DATA
        )
        applicationMetaData.metaData.putString(
            "com.google.android.gms.ads.APPLICATION_ID",
            applicationId
        )
    } catch (e: java.lang.Exception) {

    }
}