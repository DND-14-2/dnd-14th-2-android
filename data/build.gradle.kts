import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

android {
    namespace = "com.smtm.pickle.data"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val googleClientId = localProperties.getProperty("GOOGLE_WEB_CLIENT_ID")
            ?: error("local.properties에 GOOGLE_WEB_CLIENT_ID가 없습니다")

        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"$googleClientId\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false // 난독화 on/off
            val url = localProperties.getProperty("BASE_URL_DEBUG")
                ?: error("local.properties에 BASE_URL_DEBUG가 없습니다")
            buildConfigField("String", "BASE_URL", "\"$url\"")
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            val url = localProperties.getProperty("BASE_URL_RELEASE")
                ?: error("local.properties에 BASE_URL_RELEASE가 없습니다")
            buildConfigField("String", "BASE_URL", "\"$url\"")

        }

        create("qa") {
            isMinifyEnabled = false
            initWith(getByName("debug"))
            val url = localProperties.getProperty("BASE_URL_QA")
                ?: error("local.properties에 BASE_URL_QA가 없습니다")
            buildConfigField("String", "BASE_URL", "\"$url\"")
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
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)

    // Social SDK
    implementation(libs.bundles.google.login)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.bundles.testing)
}
