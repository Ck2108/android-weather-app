package com.weather.app.data.remote.dto

// =============================================================
// WeatherResponseDto.kt — "The Delivery Package (Weather)"
// =============================================================
// When we ask the weather API "What's the weather in Chicago?",
// it sends back data in JSON format (a text format computers use).
//
// JSON looks like this:
//   {
//     "current": {
//       "temperature_2m": 72.5,
//       "wind_speed_10m": 12.3
//     }
//   }
//
// But Kotlin code can't work with raw text easily. So we create
// these "DTO" classes that MIRROR the JSON structure. Gson
// automatically converts JSON → DTO objects for us!
//
// "DTO" = Data Transfer Object = "The packaging the food comes in"
//
// 🍽️ Restaurant analogy:
//   The API is like a farm that ships food in BOXES (JSON).
//   DTOs are the BOXES — they match the exact shape of what was shipped.
//   Later, we'll UNPACK the boxes into our own plates (domain model).
//
// WHY separate DTOs from domain models?
//   Because the API might change! If the API renames "temperature_2m"
//   to "temp", only THIS file changes. The rest of your app is safe.
//   At T-Mobile, the cart API might change field names in a new version.
//   DTOs absorb that change so the UI code doesn't break.
//
// "@SerializedName" = "This Kotlin field matches THIS JSON key"
//   @SerializedName("temperature_2m") val temperature: Double
//   means: JSON key "temperature_2m" → Kotlin property "temperature"
// =============================================================

import com.google.gson.annotations.SerializedName

// The TOP-LEVEL response from the weather API
data class WeatherResponseDto(
    @SerializedName("current") val current: CurrentWeatherDto,
    @SerializedName("hourly") val hourly: HourlyWeatherDto,
    @SerializedName("daily") val daily: DailyWeatherDto
)

// Current weather right now
data class CurrentWeatherDto(
    @SerializedName("temperature_2m") val temperature: Double,        // 72.5
    @SerializedName("relative_humidity_2m") val humidity: Int,         // 65
    @SerializedName("apparent_temperature") val apparentTemperature: Double, // 70.0 (feels like)
    @SerializedName("weather_code") val weatherCode: Int,              // 0 = Clear, 1 = Cloudy, etc.
    @SerializedName("wind_speed_10m") val windSpeed: Double            // 12.3
)

// Hourly forecast (arrays of data, one entry per hour)
data class HourlyWeatherDto(
    @SerializedName("time") val time: List<String>,                    // ["2024-01-01T13:00", ...]
    @SerializedName("temperature_2m") val temperature: List<Double>,   // [72.0, 74.0, ...]
    @SerializedName("weather_code") val weatherCode: List<Int>         // [0, 1, 3, ...]
)

// Daily high and low temperatures
data class DailyWeatherDto(
    @SerializedName("temperature_2m_max") val maxTemperature: List<Double>,  // [78.0]
    @SerializedName("temperature_2m_min") val minTemperature: List<Double>   // [65.0]
)
