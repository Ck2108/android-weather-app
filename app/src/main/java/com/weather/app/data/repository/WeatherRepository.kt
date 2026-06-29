package com.weather.app.data.repository

// =============================================================
// WeatherRepository.kt — "The Supplier" (Interface)
// =============================================================
// This is the INTERFACE (contract) for our data supplier.
//
// 🍽️ Restaurant analogy:
//   This is like a SUPPLY CONTRACT that says:
//   "Any supplier must be able to: get weather for a city."
//
//   The actual supplier (implementation) might:
//   - Get data from the internet (WeatherRepositoryImpl)
//   - Get data from a local database (for offline mode)
//   - Return fake data (for testing)
//
//   The ViewModel doesn't care WHERE the data comes from.
//   It just calls repository.getWeather("Chicago") and gets data.
//
// WHY an interface?
//   1. TESTING: You can create a FakeWeatherRepository for tests
//   2. FLEXIBILITY: Swap internet data for cached data without touching ViewModel
//   3. CLEAN CODE: ViewModel depends on the CONTRACT, not the implementation
//
//   At T-Mobile: CartRepository interface with
//     getCartItems(), addItem(), removeItem(), applyPromo()
//   Implementation might call the real API or a mock server.
//
// "Result<Weather>" = Either SUCCESS (with Weather data) or FAILURE (with error)
//   It's like a delivery status:
//   Result.success(weather) = "Delivery successful! Here's your food."
//   Result.failure(exception) = "Delivery failed. Reason: no internet."
// =============================================================

import com.weather.app.domain.model.Weather

interface WeatherRepository {

    // Get weather data for a city name
    // isCelsius: true = get data in °C, false = get data in °F
    // Returns Result.success(Weather) or Result.failure(Exception)
    suspend fun getWeather(cityName: String, isCelsius: Boolean = false): Result<Weather>
}
