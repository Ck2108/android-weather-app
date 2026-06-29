package com.weather.app.ui.weather

// =============================================================
// WeatherState.kt — "The Vending Machine Display"
// =============================================================
// This is the ENTIRE screen described in ONE object.
//
// 🎰 Vending machine analogy:
//    Look at a vending machine's display. At any moment, it shows:
//    - Which item is selected ("Coke")
//    - The price ("$1.50")
//    - How much money was inserted ("$0.75")
//    - Any message ("Insert $0.75 more")
//
//    Our WeatherState is EXACTLY that — a snapshot of everything
//    currently visible on the weather screen.
//
// WHY ONE OBJECT? (This is MVI's superpower!)
//    Imagine debugging a bug at T-Mobile: "The cart total is wrong."
//    If the cart state is split into 10 separate variables, good luck
//    figuring out which one is wrong!
//    But if the ENTIRE screen is ONE object, you can log it:
//      "CartState(items=[iPhone], promos=[SAVE10], total=$899, error=null)"
//    And instantly see EVERYTHING. That's why MVI uses one state object.
//
// KEY KOTLIN CONCEPTS:
//
//   "data class" = A class that's just a CONTAINER for data.
//     Kotlin automatically gives you:
//     - toString() → prints all fields nicely
//     - equals() → compares two states
//     - copy() → creates a new state with some fields changed ← THIS IS HUGE!
//
//   "val" = Cannot be changed after creation (IMMUTABLE)
//     We NEVER modify state directly. We create NEW state with copy().
//     Old state: WeatherState(temp="72°", isLoading=false)
//     New state: oldState.copy(isLoading=true)  ← creates a NEW object!
//
//   Default values (e.g., cityName: String = "")
//     When you create WeatherState(), fields start with these defaults.
//     So WeatherState() = empty screen, nothing loaded yet.
// =============================================================

import com.weather.app.ui.components.HourlyWeather

data class WeatherState(
    // ---- What the user sees ----
    val cityName: String = "",              // "San Francisco"
    val temperature: String = "",           // "72°F"
    val condition: String = "",             // "Sunny"
    val weatherEmoji: String = "🌤️",       // Emoji for the weather
    val highTemp: String = "",              // "78°"
    val lowTemp: String = "",               // "65°"
    val windSpeed: String = "",             // "12 mph"
    val humidity: String = "",              // "65%"
    val feelsLike: String = "",             // "70°F"
    val hourlyForecast: List<HourlyWeather> = emptyList(),  // List of hourly data

    // ---- Screen state ----
    val isLoading: Boolean = false,         // Show a loading spinner?
    val error: String? = null,              // Error message (null = no error)

    // ---- Search ----
    val searchQuery: String = "",           // What the user typed in search bar

    // ---- User preferences ----
    // NEW! Toggle between Fahrenheit and Celsius
    // false = °F (default), true = °C
    val isCelsius: Boolean = false
)

// =============================================================
// 🧪 TRY THIS TO UNDERSTAND copy():
// =============================================================
// val state1 = WeatherState(cityName = "Chicago", temperature = "65°F")
// val state2 = state1.copy(temperature = "70°F")
//
// state1.temperature → "65°F"  (UNCHANGED! state1 is still the same)
// state2.temperature → "70°F"  (NEW object with updated temperature)
// state2.cityName    → "Chicago" (copied from state1, wasn't changed)
//
// This is how MVI updates the screen:
//   1. Something happens (user types, data arrives, error occurs)
//   2. We call currentState.copy(changedField = newValue)
//   3. Compose sees the new state and REDRAWS only what changed
//
// At T-Mobile:
//   val cartState = CartState(items = [iPhone], total = $999)
//   val newState = cartState.copy(total = $899)  // Applied a $100 promo!
//   // items stayed the same, only total changed
// =============================================================
