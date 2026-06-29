package com.weather.app.data.repository

// =============================================================
// WeatherRepositoryImpl.kt — "The Actual Supplier" (Implementation)
// =============================================================
// This is the REAL supplier that actually fetches data from the internet.
//
// The flow:
//   1. Receive city name ("Chicago")
//   2. Call GEOCODING API → get coordinates (lat: 41.85, lon: -87.65)
//   3. Call WEATHER API with coordinates → get weather data
//   4. Convert DTO → Domain Model using the mapper
//   5. Return the result (success or failure)
//
// 🍽️ Restaurant analogy:
//   1. Customer orders "Chicago pizza" (city name)
//   2. Supplier looks up Mario's Pizza address (geocoding)
//   3. Drives to Mario's and picks up the pizza (weather API)
//   4. Plates it nicely (mapper)
//   5. Delivers to the restaurant (returns Result)
// =============================================================

import com.weather.app.data.mapper.toDomainModel
import com.weather.app.data.remote.AirQualityApiService
import com.weather.app.data.remote.GeocodingApiService
import com.weather.app.data.remote.WeatherApiService
import com.weather.app.domain.model.Weather
import com.weather.app.util.WeatherLogger

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApiService,
    private val geocodingApi: GeocodingApiService,
    private val airQualityApi: AirQualityApiService
) : WeatherRepository {
    // "WeatherRepositoryImpl : WeatherRepository" means:
    // "This class IMPLEMENTS the WeatherRepository contract"
    // It MUST provide the getWeather() function that the interface promises.

    override suspend fun getWeather(cityName: String, isCelsius: Boolean): Result<Weather> {
        WeatherLogger.d("Repository: fetching weather for '$cityName' (celsius=$isCelsius)")
        return try {
            // STEP 1: Convert city name → coordinates using geocoding API
            // "Chicago" → { latitude: 41.85, longitude: -87.65 }
            val geocodingResponse = geocodingApi.searchCity(name = cityName)

            // Check if we got results
            val location = geocodingResponse.results?.firstOrNull()
                ?: return Result.failure(Exception("City '$cityName' not found. Try a different name."))
            // "?." = safe call (if results is null, don't crash)
            // "firstOrNull()" = get the first result, or null if list is empty
            // "?:" (elvis operator) = "if the left side is null, do the right side"

            // STEP 2: Use coordinates to get weather data
            // Pass the temperature unit based on user preference!
            val weatherResponse = weatherApi.getWeather(
                latitude = location.latitude,
                longitude = location.longitude,
                // THIS is where the unit toggle takes effect:
                // isCelsius=true → "celsius", isCelsius=false → "fahrenheit"
                temperatureUnit = if (isCelsius) "celsius" else "fahrenheit",
                windSpeedUnit = if (isCelsius) "kmh" else "mph"
            )

            // STEP 3: Try to fetch AQI — GRACEFUL FALLBACK if it fails
            // runCatching = "try this, if it throws ANY exception, give me -1 instead"
            // WHY -1? It's a sentinel value meaning "data unavailable". The mapper
            // converts -1 → "--" label. The app never crashes because of AQI.
            // This is exactly what the user decided: weather always shows, AQI shows "--" on failure.
            val airQualityIndex = runCatching {
                airQualityApi.getAirQuality(
                    latitude = location.latitude,
                    longitude = location.longitude
                ).current.europeanAqi
            }.getOrElse { error ->
                WeatherLogger.e("Repository: AQI fetch failed (graceful fallback)", error)
                -1  // -1 = unavailable → mapper will show "--"
            }

            // STEP 4: Convert the DTO → Domain Model using our mapper
            // Use the location name from geocoding (it's properly capitalized)
            val displayName = if (location.admin1 != null) {
                "${location.name}, ${location.admin1}"  // "Chicago, Illinois"
            } else {
                location.name  // "Chicago"
            }
            // Pass airQualityIndex into the mapper — it handles label conversion there
            val weather = weatherResponse.toDomainModel(displayName, airQualityIndex)

            // STEP 5: Return success!
            WeatherLogger.d("Repository: success for '$displayName' (AQI=$airQualityIndex)")
            Result.success(weather)

        } catch (e: Exception) {
            // BONUS DEBUG LESSON: Log the REAL error for developers, show friendly message to users.
            WeatherLogger.e("Repository: network/API failure for '$cityName'", e)
            Result.failure(Exception("Couldn't load weather. Check your internet connection."))
        }
    }
}
