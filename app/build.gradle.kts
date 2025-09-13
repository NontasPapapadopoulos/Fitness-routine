plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "nondas.pap.fitness_routine"
    compileSdk = 36

    defaultConfig {
        applicationId = "nondas.pap.fitness_routine"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += listOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)


    // navigation
    //implementation(libs.hilt.navigation.compose)

//     Core runtime for Jetpack Navigation 3 library — provides navigation components and APIs
    implementation(libs.androidx.navigation3.runtime)

    // UI components for Navigation 3 — includes NavDisplay etc.
    implementation(libs.androidx.navigation3.ui)

    // ViewModel integration with Navigation 3 — provides lifecycle-aware ViewModels scoped to navigation destinations
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.activity.compose)
    implementation(libs.compose.material3)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    // ViewModel & Lifecycle
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // Status bar customization
    implementation(libs.accompanist.systemuicontroller)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    testImplementation(libs.room.testing)
    ksp(libs.room.compiler)

    // Compose Material Theme
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.material3)

    // DataStore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.rxjava2)
    implementation(libs.datastore.preferences.rxjava3)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)


    // Kotlin Serialization
    implementation(libs.serialization.json)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.navigation.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)


}
