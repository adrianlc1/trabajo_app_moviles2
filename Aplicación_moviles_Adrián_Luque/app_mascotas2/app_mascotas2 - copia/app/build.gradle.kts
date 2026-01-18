plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "es.maestre.app_mascotas"
    compileSdk = 36

    defaultConfig {
        applicationId = "es.maestre.app_mascotas"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.runtime)
    kapt("com.google.dagger:hilt-compiler:2.51.1")


    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")


    val supabase_v = "3.0.1"
    implementation("io.github.jan-tennert.supabase:postgrest-kt:$supabase_v")
    implementation("io.github.jan-tennert.supabase:auth-kt:$supabase_v")
    implementation("io.github.jan-tennert.supabase:storage-kt:$supabase_v")
    implementation("io.ktor:ktor-client-android:2.3.12")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("com.github.bumptech.glide:glide:4.16.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val supabase_version = "3.0.2"
    implementation("io.github.jan-tennert.supabase:postgrest-kt:$supabase_version")


    implementation("io.ktor:ktor-client-android:3.0.1")
    implementation("io.ktor:ktor-client-core:3.0.1")
    implementation("com.google.android.material:material:1.11.0")


    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.compose.ui:ui:1.5.0")


    implementation("androidx.compose.material3:material3:1.1.0")


    implementation("io.coil-kt:coil-compose:2.4.0")


    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation("androidx.activity:activity-compose:1.8.0")

    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    implementation("io.ktor:ktor-client-android:2.3.7")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")

    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")

    implementation("androidx.datastore:datastore-preferences:1.2.0")



}