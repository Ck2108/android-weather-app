package com.weather.app.data.remote

// =============================================================
// WeatherApiService.kt — "The Delivery App Menu"
// =============================================================
// This is a Retrofit INTERFACE that defines what API calls we can make.
//
// Think of it like a delivery app's menu:
//   - "Get weather for coordinates" → one menu item
//   - "Search for a city name" → another menu item
//
// HOW RETROFIT WORKS (the magic):
//   1. You write an INTERFACE (just the menu — what's available)
//   2. Retrofit AUTOMATICALLY creates the actual code that makes
//      HTTP requests, sends data, and parses responses
//   3. You just call the function like it's a normal Kotlin function!
//
//   It's like writing a wish list: "I want a function that fetches weather"
//   And Retrofit says: "Done! I built it for you."
//
// KEY CONCEPTS:
//
//   "interface" = A CONTRACT (a promise of what functions exist).
//     Unlike a "class", an interface has no code — just signatures.
//     Retrofit fills in the code at runtime.
//
//   "suspend fun" = A function that can PAUSE and wait for a result
//     without freezing the app. Called from a coroutine (viewModelScope).
//
//   "@GET" = "Make an HTTP GET request to this URL path"
//     GET = "give me data" (like browsing a website)
//     POST = "save new data" (like submitting a form)
//     PUT = "update existing data"
//     DELETE = "remove data"
//
//   "@Query" = Add a parameter to the URL
//     @Query("name") cityName: String → ?name=Chicago
//     @Query("count") count: Int → ?count=1
//     Full URL becomes: baseUrl/v1/search?name=Chicago&count=1
// =============================================================

import com.weather.app.data.remote.dto.GeocodingResponseDto
import com.weather.app.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApiService {

    // =============================================================
    // Get weather data for specific coordinates (lat/lng)
    // =============================================================
    // Base URL: https://api.open-meteo.com/
    // Full URL: https://api.open-meteo.com/v1/forecast?latitude=41.85&longitude=-87.65&...
    //
    // The @Query parameters with default values mean:
    //   "Always include these in the request unless told otherwise"
    // =============================================================
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        // Which current weather fields we want
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m",
        // Which hourly fields we want
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        // Which daily fields we want
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        // Use Fahrenheit (not Celsius)
        @Query("temperature_unit") temperatureUnit: String = "fahrenheit",
        // Wind speed in mph (not km/h)
        @Query("wind_speed_unit") windSpeedUnit: String = "mph",
        // Automatically detect timezone
        @Query("timezone") timezone: String = "auto",
        // Only get forecast for today (1 day)
        @Query("forecast_days") forecastDays: Int = 1
    ): WeatherResponseDto
}

// =============================================================
// Geocoding API (separate interface — different base URL)
// =============================================================
// Converts city names → coordinates
// Base URL: https://geocoding-api.open-meteo.com/
// =============================================================
interface GeocodingApiService {

    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,    // The city name to search for
        @Query("count") count: Int = 5  // How many results to return
    ): GeocodingResponseDto
}
