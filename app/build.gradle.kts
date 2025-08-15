plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // Iski yahan zaroorat hai
    alias(libs.plugins.google.gms.google.services)
    id("kotlinx-serialization")
    alias(libs.plugins.google.dagger.hilt.android) // <-- Ab ye kaam karega
    kotlin("kapt")
    
}

android {
    namespace = "com.yogesh.stylish"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.yogesh.stylish"
        minSdk = 31
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.google.material)
    // Jetpack Lifecycle


    implementation(libs.play.services.gcm)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    
    

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Jetpack DataStore (for saving user preferences like onboarding status)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.androidx.preference.ktx)

    implementation("io.ktor:ktor-client-android:2.3.10")

    // Ktor Content Negotiation - Ye JSON ko samajhne me help karta hai
    implementation("io.ktor:ktor-client-content-negotiation:2.3.10")

    // Kotlinx Serialization - Ye JSON ko aapki data class me badalta hai
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.10")

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
