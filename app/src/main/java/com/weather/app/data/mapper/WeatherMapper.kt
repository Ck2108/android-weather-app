package com.weather.app.data.mapper

// =============================================================
// WeatherMapper.kt — "The Chef Who Plates the Food"
// =============================================================
// This converts DTOs (delivery boxes) → Domain Model (plated food).
//
// The API sends raw data:
//   { "weather_code": 0, "temperature_2m": 72.5 }
//
// We convert it to something meaningful:
//   Weather(condition = "Clear Sky", emoji = "☀️", temperature = 72.5)
//
// The mapper also handles the WEATHER CODE → EMOJI conversion.
// The Open-Meteo API uses number codes for weather conditions:
//   0 = Clear sky ☀️
//   1 = Partly cloudy 🌤️
//   61 = Rain 🌧️
//   95 = Thunderstorm ⛈️
// =============================================================

import com.weather.app.data.remote.dto.WeatherResponseDto
import com.weather.app.domain.model.Weather

// =============================================================
// Main mapper function: DTO → Domain Model
// =============================================================
// "fun WeatherResponseDto.toDomainModel()" — This is an EXTENSION FUNCTION.
//
// Extension function = Adding a new function to an EXISTING class
// without modifying the class itself. It's like giving someone
// a new skill: "Hey WeatherResponseDto, you can now convert yourself!"
//
// Usage: val weather = responseDto.toDomainModel("Chicago")
// =============================================================
fun WeatherResponseDto.toDomainModel(cityName: String, airQualityIndex: Int = -1): Weather {
    return Weather(
        cityName = cityName,
        temperature = current.temperature,
        condition = weatherCodeToCondition(current.weatherCode),
        weatherCode = current.weatherCode,
        highTemp = daily.maxTemperature.firstOrNull() ?: current.temperature,
        lowTemp = daily.minTemperature.firstOrNull() ?: current.temperature,
        windSpeed = current.windSpeed,
        humidity = current.humidity,
        feelsLike = current.apparentTemperature,
        hourlyTemperatures = hourly.temperature,
        hourlyWeatherCodes = hourly.weatherCode,
        hourlyTimes = hourly.time,
        sunrise = formatSunTime(daily.sunrise.firstOrNull()),
        sunset = formatSunTime(daily.sunset.firstOrNull()),
        airQualityIndex = airQualityIndex,          // raw Int from AQI API (-1 if unavailable)
        airQualityLabel = aqiToLabel(airQualityIndex) // Mapper converts it — ViewModel stays clean!
    )
}

// =============================================================
// Weather Code → Condition Name
// =============================================================
// The Open-Meteo API uses WMO weather codes (international standard).
// We convert the number to a human-readable condition name.
// =============================================================
fun weatherCodeToCondition(code: Int): String {
    return when (code) {
        0 -> "Clear Sky"
        1 -> "Mainly Clear"
        2 -> "Partly Cloudy"
        3 -> "Overcast"
        45, 48 -> "Foggy"
        51, 53, 55 -> "Drizzle"
        56, 57 -> "Freezing Drizzle"
        61, 63, 65 -> "Rainy"
        66, 67 -> "Freezing Rain"
        71, 73, 75 -> "Snowy"
        77 -> "Snow Grains"
        80, 81, 82 -> "Rain Showers"
        85, 86 -> "Snow Showers"
        95 -> "Thunderstorm"
        96, 99 -> "Thunderstorm with Hail"
        else -> "Unknown"
    }
}

// =============================================================
// Weather Code → Emoji
// =============================================================
// Converts the weather code to an emoji for the UI.
// =============================================================
fun weatherCodeToEmoji(code: Int): String {
    return when (code) {
        0 -> "☀️"           // Clear sky
        1 -> "🌤️"          // Mainly clear
        2 -> "⛅"           // Partly cloudy
        3 -> "☁️"           // Overcast
        45, 48 -> "🌫️"     // Fog
        51, 53, 55 -> "🌦️" // Drizzle
        56, 57 -> "🌧️"     // Freezing drizzle
        61, 63, 65 -> "🌧️" // Rain
        66, 67 -> "🌧️"     // Freezing rain
        71, 73, 75 -> "❄️"  // Snow
        77 -> "❄️"          // Snow grains
        80, 81, 82 -> "🌧️" // Rain showers
        85, 86 -> "🌨️"     // Snow showers
        95 -> "⛈️"          // Thunderstorm
        96, 99 -> "⛈️"      // Thunderstorm with hail
        else -> "🌤️"       // Default
    }
}

// =============================================================
// Format hourly time: "2024-01-01T13:00" → "1 PM"
// =============================================================
fun formatHourlyTime(isoTime: String): String {
    return try {
        // Extract the hour from "2024-01-01T13:00"
        val hour = isoTime.substringAfter("T").substringBefore(":").toInt()
        when {
            hour == 0 -> "12 AM"
            hour < 12 -> "$hour AM"
            hour == 12 -> "12 PM"
            else -> "${hour - 12} PM"
        }
    } catch (e: Exception) {
        isoTime  // If parsing fails, just show the raw time
    }
}

// =============================================================
// Format sunrise/sunset time: "2024-01-01T06:45" → "6:45 AM"
// =============================================================
// WHY nullable input? The API might not return sunrise/sunset for
// some locations (e.g., extreme latitudes like Alaska in winter).
// firstOrNull() returns null if the list is empty — we handle that
// gracefully with "N/A" instead of crashing.
// =============================================================
fun formatSunTime(isoTime: String?): String {
    if (isoTime == null) return "N/A"
    return try {
        // "2024-01-01T06:45" → take everything after "T" → "06:45"
        val timePart = isoTime.substringAfter("T")          // "06:45"
        val hour = timePart.substringBefore(":").toInt()    // 6
        val minute = timePart.substringAfter(":").toInt()   // 45
        val minuteStr = minute.toString().padStart(2, '0')  // "45" (always 2 digits)
        when {
            hour == 0  -> "12:${minuteStr} AM"
            hour < 12  -> "${hour}:${minuteStr} AM"
            hour == 12 -> "12:${minuteStr} PM"
            else       -> "${hour - 12}:${minuteStr} PM"
        }
    } catch (e: Exception) {
        "N/A"  // If anything goes wrong, show N/A — never crash the app
    }
}

// =============================================================
// AQI Int → human-readable label
// =============================================================
// WHY here and not in ViewModel? Because the user correctly identified:
// "All conversions happen in the Mapper. ViewModel stays clean."
// The ViewModel just passes weather.airQualityLabel straight to state.
//
// -1 is our sentinel value meaning "AQI fetch failed gracefully".
// =============================================================
fun aqiToLabel(index: Int): String = when {
    index < 0    -> "--"          // -1 = fetch failed, graceful fallback
    index <= 50  -> "Good"
    index <= 100  -> "Moderate"
    index <= 150  -> "Unhealthy for Sensitive Groups"
    index <= 200  -> "Unhealthy"
    index <= 500 -> "Very Unhealthy"
    else         -> "Hazardous"
}
