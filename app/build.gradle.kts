import java.io.FileInputStream
import java.util.*

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.github.triplet.play)
}

android {
    namespace = "com.devexperto.gradletraining"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devexperto.gradletraining"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Load keystore
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file("../${keystoreProperties["storeFile"]}")
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        create("beta") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    // Create product flavors
    flavorDimensions.addAll(listOf("version", "abi", "server"))

    productFlavors {
        create("free") {
            dimension = "version"
        }
        create("premium") {
            dimension = "version"
            applicationIdSuffix = ".premium"
        }

        create("arm") {
            dimension = "abi"
        }
        create("arm64") {
            dimension = "abi"
        }
        create("x86") {
            dimension = "abi"
        }

        create("dev") {
            dimension = "server"
            applicationIdSuffix = ".dev"
        }
        create("staging") {
            dimension = "server"
            applicationIdSuffix = ".staging"
        }
        create("prod") {
            dimension = "server"
            applicationIdSuffix = ".prod"
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

play {
    serviceAccountCredentials.set(file("../service-key.json"))
}

dependencies {
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.ktx)
    implementation(libs.bundles.androidx.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)
}
