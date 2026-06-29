// =============================================================
// build.gradle.kts (APP MODULE) — "How to build OUR app"
// =============================================================
// This is the MAIN recipe for building the Weather App.
//
// It has 3 sections:
//   1. PLUGINS — Turn on the tools we declared in the project build file
//   2. ANDROID — Configure Android-specific stuff (SDK version, app ID)
//   3. DEPENDENCIES — List all the libraries (ingredients) we need
//
// 🍽️ Restaurant analogy: The project build.gradle said "we have
//    an oven and stove." THIS file says "Turn on the oven to 350°F,
//    and here's the ingredient list for our cake."
// =============================================================

// ----- SECTION 1: PLUGINS (Turn on the tools) -----
plugins {
    // "This module is an Android app" (not a library, not a website — an APP)
    id("com.android.application")

    // "We write code in Kotlin" (not Java)
    id("org.jetbrains.kotlin.android")

    // "We use Jetpack Compose for UI" (the LEGO block system)
    id("org.jetbrains.kotlin.plugin.compose")
}

// ----- SECTION 2: ANDROID CONFIG -----
android {
    // Your app's unique address on the Play Store (like a website URL)
    // At T-Mobile, this might be "com.tmobile.app"
    namespace = "com.weather.app"

    // Which Android version to compile against
    // SDK 34 = Android 14. Think of it as "we can use features up to Android 14"
    compileSdk = 34

    defaultConfig {
        // The unique ID for your app — no two apps on the Play Store can have the same one
        applicationId = "com.weather.app"

        // Minimum Android version our app works on
        // SDK 26 = Android 8.0. Phones older than this CAN'T install our app.
        minSdk = 26

        // The Android version we TESTED against and optimized for
        targetSdk = 34

        // Version tracking — increment these when you release updates
        versionCode = 1       // Internal number (1, 2, 3, 4...)
        versionName = "1.0"   // What users see ("1.0", "1.1", "2.0")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // isMinifyEnabled = true would shrink the app for release
            // We keep it false during development for easier debugging
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Use Java 11 features
    // (Java 11 matches what's installed on your computer)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Tell Kotlin to also target Java 11
    kotlinOptions {
        jvmTarget = "11"
    }

    // Turn on Compose (our LEGO block UI system)
    buildFeatures {
        compose = true
    }
}

// ----- SECTION 3: DEPENDENCIES (Libraries / Ingredients) -----
// Each line is a library someone else wrote that we're using.
// "implementation" = "we need this to RUN the app"
// "debugImplementation" = "we only need this during DEVELOPMENT"
dependencies {

    // ===== COMPOSE (Our UI Framework — the LEGO blocks) =====
    // BOM = "Bill of Materials" — ONE version number controls ALL Compose libraries
    // This way, all Compose libraries are guaranteed to work together.
    // It's like buying a LEGO set — all pieces are from the same set, so they fit.
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)

    implementation("androidx.compose.ui:ui")                    // Core Compose UI
    implementation("androidx.compose.ui:ui-graphics")           // Drawing & graphics
    implementation("androidx.compose.ui:ui-tooling-preview")    // Preview in Android Studio
    implementation("androidx.compose.material3:material3")      // Material Design 3 components
    implementation("androidx.compose.material:material")          // Pull-to-refresh support

    // ===== ACTIVITY + LIFECYCLE (Connecting Compose to Android) =====
    // Activity = The "window" that holds your app's screen
    implementation("androidx.activity:activity-compose:1.9.0")

    // Lifecycle = Knows when your app is open, in background, or closed
    // This helps the ViewModel survive screen rotations
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    // ===== NETWORKING (Fetching data from the internet) =====
    // These are like the DELIVERY SERVICE for our restaurant.
    // Instead of growing our own food, we order it from a farm (API).

    // Retrofit = The delivery app (like DoorDash/UberEats)
    // It knows HOW to make internet requests and get responses back.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter = The TRANSLATOR between JSON and Kotlin
    // The API sends data as JSON text: {"temp": 72, "city": "Chicago"}
    // Gson converts that into a Kotlin object we can use in our code.
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp = The actual DELIVERY TRUCK that carries the requests
    // Retrofit tells OkHttp WHERE to go, OkHttp does the driving.
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Logging Interceptor = A GPS TRACKER on the delivery truck
    // Shows us in Logcat exactly what request was sent and what came back.
    // Super useful for debugging! "Why is the weather data wrong?"
    // → Check the log, see the exact API response.
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ===== DEBUG TOOLS (Only in development builds) =====
    debugImplementation("androidx.compose.ui:ui-tooling")       // Visual debugger
    debugImplementation("androidx.compose.ui:ui-test-manifest") // Testing support
}
