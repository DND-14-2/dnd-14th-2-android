plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.smtm.pickle.presentation"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val appVersionCode = (project.property("VERSION_CODE") as String).toInt()
        val appVersionName = project.property("VERSION_NAME") as String

        buildConfigField("String", "VERSION_NAME", "\"$appVersionName\"")
        buildConfigField("int", "VERSION_CODE", "$appVersionCode")
    }

    buildTypes {
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
    lint {
        lintConfig = file("lint.xml")
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.bundles.androidx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Social SDK
    implementation(libs.kakao.user)

    // Logging
    implementation(libs.timber)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Calendar
    implementation(libs.kizitonwose.calendar.compose)

    testImplementation(libs.bundles.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
