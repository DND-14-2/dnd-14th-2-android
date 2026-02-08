import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

fun Properties.require(key: String): String =
    getProperty(key) ?: throw GradleException("local.properties에 '$key'가 설정되지 않았습니다.")

android {
    namespace = "com.smtm.pickle"
    compileSdk {
        version = release(36)
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(localProperties.require("DEBUG_KEYSTORE_PATH"))
            storePassword = localProperties.require("DEBUG_KEYSTORE_PASSWORD")
            keyAlias = localProperties.require("DEBUG_KEY_ALIAS")
            keyPassword = localProperties.require("DEBUG_KEY_PASSWORD")
        }
    }

    defaultConfig {
        applicationId = "com.smtm.pickle"
        minSdk = 30
        targetSdk = 36
        versionCode = libs.versions.pickleVersionCode.get().toInt()
        versionName = libs.versions.pickleVersionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // 키값이 없음을 빠르게 확인용
        val kakaoKey = localProperties.require("KAKAO_NATIVE_APP_KEY")

        // Manifest에 주입
        manifestPlaceholders["NATIVE_APP_KEY"] = kakaoKey
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoKey\"")
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("qa") {
            initWith(getByName("debug"))
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
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":data"))

    implementation(libs.bundles.androidx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Logging
    implementation(libs.timber)

    // Social SDK
    implementation(libs.kakao.user)

    // Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
