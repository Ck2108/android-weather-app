package com.weather.app.domain.model

// =============================================================
// Weather.kt — "The Food on Your Plate" (Domain Model)
// =============================================================
// This is the CLEAN version of weather data that our app uses.
//
// 🍽️ Restaurant analogy:
//   - DTO (WeatherResponseDto) = Food in delivery packaging/boxes
//   - Domain Model (Weather) = Food plated nicely on YOUR plate
//
// WHY have a separate model?
//   The API sends data in its own format:
//     { "temperature_2m": 72.5, "weather_code": 0 }
//
//   But we want cleaner names and types:
//     Weather(temperature = 72.5, condition = "Clear Sky", emoji = "☀️")
//
//   The domain model is what the REST of the app works with.
//   Only the data layer (DTOs + mapper) knows about the API's format.
//
//   At T-Mobile:
//   - DTO: CartItemDto(sku="IPHONE15PRO256GB", price_cents=99900)
//   - Domain: CartItem(name="iPhone 15 Pro", price="$999.00", storage="256GB")
// =============================================================

data class Weather(
    val cityName: String,              // "Chicago"
    val temperature: Double,           // 72.5 (in °F)
    val condition: String,             // "Clear Sky"
    val weatherCode: Int,              // 0 (used to pick emoji)
    val highTemp: Double,              // 78.0
    val lowTemp: Double,               // 65.0
    val windSpeed: Double,             // 12.3 (in mph)
    val humidity: Int,                 // 65 (percentage)
    val feelsLike: Double,             // 70.0
    val hourlyTemperatures: List<Double>,   // [72.0, 74.0, 76.0, ...]
    val hourlyWeatherCodes: List<Int>,      // [0, 1, 3, ...]
    val hourlyTimes: List<String>           // ["2024-01-01T13:00", ...]
)
