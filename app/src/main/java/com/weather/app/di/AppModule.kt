package com.weather.app.di

// =============================================================
// AppModule.kt — "The Phone Book" (Dependency Injection)
// =============================================================
// This file creates and wires together all the pieces our app needs.
//
// "Dependency Injection" sounds scary, but it's simple:
//   It means "GIVING someone what they need instead of them creating it."
//
// 🍽️ Restaurant analogy:
//   Without DI: Each chef makes their own knife from scratch. Wasteful!
//   With DI:    The manager GIVES each chef a knife from the supply room.
//
// In code terms:
//   Without DI: ViewModel creates its own Retrofit, its own OkHttp, its own Repository...
//   With DI:    ViewModel RECEIVES a ready-made Repository. It doesn't know how it was built.
//
// WHY? Because:
//   1. You only create expensive objects ONCE (one Retrofit instance, not many)
//   2. Testing is easy (give the ViewModel a fake repository during tests)
//   3. Changing implementation is easy (swap real API for cached data)
//
// At T-Mobile, they use "Hilt" (a DI framework by Google) to do this automatically.
// We're doing it MANUALLY here so you understand the concept first.
// Once you get Hilt, you'll think "Oh, Hilt just automates what we did here!"
// =============================================================

import com.weather.app.data.remote.AirQualityApiService
import com.weather.app.data.remote.GeocodingApiService
import com.weather.app.data.remote.WeatherApiService
import com.weather.app.data.repository.WeatherRepository
import com.weather.app.data.repository.WeatherRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// "object" = A SINGLETON — only ONE instance exists in the entire app.
// Perfect for things we want to create once and reuse everywhere.
object AppModule {

    // =============================================================
    // Step 1: Create the OkHttp client (the delivery truck)
    // =============================================================
    // We add a logging interceptor so we can see every request/response
    // in Android Studio's Logcat. Super helpful for debugging!
    private val okHttpClient: OkHttpClient by lazy {
        // "by lazy" = "Don't create this until someone first asks for it,
        //              then keep it forever." Saves startup time!
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    // BODY = log everything including request/response bodies
                    // In production, you'd use BASIC or NONE
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    // =============================================================
    // Step 2: Create the Weather API service
    // =============================================================
    // Base URL = "https://api.open-meteo.com/"
    // Retrofit builds the actual HTTP client from our interface.
    private val weatherApi: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(okHttpClient)                        // Use our OkHttp client
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON
            .build()
            .create(WeatherApiService::class.java)       // Create the service!
            // This single line is Retrofit's MAGIC:
            // It reads our interface and GENERATES the implementation!
    }

    // =============================================================
    // Step 3: Create the Geocoding API service
    // =============================================================
    // Different base URL for city name → coordinates lookup
    private val geocodingApi: GeocodingApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .client(okHttpClient)                        // Same truck, different destination
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }

    // =============================================================
    // Step 4: Create the Air Quality API service
    // =============================================================
    // Third base URL — completely separate server for pollution data.
    // WHY same okHttpClient? The "truck" (HTTP client with logging)
    // is reusable. Only the "destination" (base URL) changes.
    private val airQualityApi: AirQualityApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://air-quality-api.open-meteo.com/")
            .client(okHttpClient)                        // Same truck, third destination
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirQualityApiService::class.java)
    }

    // =============================================================
    // Step 5: Create the Repository (was Step 4)
    // =============================================================
    // Now the repository gets THREE API services — it can call all three!
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherApi = weatherApi,
            geocodingApi = geocodingApi,
            airQualityApi = airQualityApi
        )
    }
}
