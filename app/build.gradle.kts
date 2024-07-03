import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.fittracker"
    compileSdk = 34

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
    }

    defaultConfig {
        applicationId = "com.example.fittracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xenable-scripting-support")
    }
    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.williamchart)
    implementation(libs.mpandroidchart)
    implementation(libs.jetbrains.kotlin.scripting.compiler.embeddable)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.runner)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.rules)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.ext.junit)
    testImplementation(libs.fragment.testing)
    testImplementation(libs.mockito.core)

    add("ksp", "androidx.room:room-compiler:2.6.1")
}