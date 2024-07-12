import com.android.build.api.variant.BuildConfigField

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.22"
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.service.composemedia"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
       // Declare addMobId string key
        buildConfigField("String", "ADMOB_APP_ID", "\"ca-app-pub-4241681549957796~8958488308\"")
        buildConfigField("String", "ADMOB_AD_UNIT_ID", "\"ca-app-pub-4241681549957796/5652500824\"")
    }

    buildTypes {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    namespace = "com.service.composemedia"
}

dependencies {
    val room_version = "2.6.1"
    val retrofit = "2.9.0"
    val okhttp = "4.11.0"
    val admob_version = "21.1.0"

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.activity.compose)
    implementation(libs.constraintlayout.compose)
    implementation(libs.palette.ktx)
    implementation(libs.navigation.compose)

    // Lifecycle
    implementation(libs.lifecycle.extensions)
    implementation(libs.bundles.lifecycle)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Coil
    implementation(libs.coil)

    //Accompanist
    implementation(libs.bundles.accompanist)

    // Dagger - Hilt
    implementation(libs.bundles.hilt)
    implementation(libs.media3.ui)
    kapt(libs.bundles.hilt.kapt)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // ExoPlayer
//    api(libs.bundles.exoplayer)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)

    implementation ("com.google.accompanist:accompanist-coil:0.15.0")

    implementation("me.onebone:toolbar-compose:2.3.5")

    //Room DB
    implementation ( "androidx.room:room-runtime:$room_version")
    kapt ( "androidx.room:room-compiler:$room_version")
    implementation ( "androidx.room:room-ktx:$room_version")
    implementation ( "androidx.room:room-rxjava2:$room_version")

    //Retrofit
    implementation (  "com.squareup.retrofit2:retrofit:$retrofit")
    implementation (  "com.squareup.okhttp3:okhttp:$okhttp")
    implementation (  "com.squareup.retrofit2:converter-gson:$retrofit")


    //ads
    implementation ("com.google.android.gms:play-services-ads:$admob_version")

}