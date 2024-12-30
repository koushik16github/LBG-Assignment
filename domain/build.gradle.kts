plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.plugin)
    kotlin("kapt")
}

android {
    namespace = "com.koushik.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // MockK for mocking
    testImplementation(libs.mockk)

    // JUnit for testing
    testImplementation(libs.junit)

    // Coroutines for testing
    testImplementation(libs.coroutines.test)

    // Hilt Testing dependencies
    testImplementation(libs.hilt.android.testing)

    // Lifecycle Testing dependencies
    testImplementation(libs.arch.core.testing)
}