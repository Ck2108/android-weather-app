package com.weather.app.ui.weather

// =============================================================
// WeatherIntent.kt — "The Vending Machine Buttons"
// =============================================================
// These are ALL the things a user CAN DO on our screen.
//
// 🎰 Vending machine analogy:
//    A vending machine has a FIXED set of buttons:
//    - "Select Coke" button
//    - "Insert Coin" slot
//    - "Cancel" button
//    You CAN'T invent new buttons on the spot.
//
//    Similarly, our app has a FIXED set of user actions:
//    - Search for a city
//    - Update the search text
//    - Refresh the weather
//    - Retry after an error
//    Nothing else is possible. This makes our app PREDICTABLE.
//
// KEY KOTLIN CONCEPT: "sealed interface"
//
//    "sealed" = "This is a CLOSED set. No one can add more options later."
//
//    Why sealed? Because Kotlin's "when" statement will FORCE you to
//    handle EVERY possible intent. If you forget one, the code won't compile!
//
//    when (intent) {
//        is WeatherIntent.SearchCity → // handle it
//        is WeatherIntent.UpdateSearchQuery → // handle it
//        is WeatherIntent.Refresh → // handle it
//        is WeatherIntent.Retry → // handle it
//        // If you forget one ↑ Kotlin says "ERROR: you missed one!"
//    }
//
//    This prevents bugs! At T-Mobile, if someone adds a new button
//    "CartIntent.ApplyPromo", the compiler forces EVERY developer
//    to handle it. No one can accidentally forget.
//
// "data class" vs "data object":
//    - "data class SearchCity(val query: String)"
//        → An intent that CARRIES DATA. Which city? "Chicago".
//    - "data object Refresh"
//        → An intent with NO extra data. Just "refresh!" No details needed.
//
//    Think of it like:
//    - "I want Coke" needs to say WHICH drink → data class (carries info)
//    - "Cancel" doesn't need extra info → data object (just the action)
// =============================================================

sealed interface WeatherIntent {

    // User typed something in the search bar
    // Carries the new text as data
    // Example: UpdateSearchQuery("Chi") → user typed "Chi"
    data class UpdateSearchQuery(val query: String) : WeatherIntent

    // User pressed "search" — go find weather for the typed city
    // Carries the city name to search for
    // Example: SearchCity("Chicago")
    data class SearchCity(val query: String) : WeatherIntent

    // User pulled to refresh — reload current city's weather
    // No extra data needed — just "refresh whatever is showing"
    data object Refresh : WeatherIntent

    // User tapped "Retry" after an error — try the last action again
    // No extra data needed — just "try again"
    data object Retry : WeatherIntent

    // NEW! User tapped the °F/°C toggle button
    // No extra data needed — just "flip the temperature unit"
    // This is a "data object" because we don't need to say WHICH unit —
    // the ViewModel already knows the current unit and will flip it.
    data object ToggleTemperatureUnit : WeatherIntent
}

// =============================================================
// 🧪 HOW INTENTS WILL BE USED (preview of ViewModel):
// =============================================================
// In the ViewModel (brain), we'll have a function like:
//
//   fun processIntent(intent: WeatherIntent) {
//       when (intent) {
//           is WeatherIntent.UpdateSearchQuery → {
//               // Just update the search text in state
//               state = state.copy(searchQuery = intent.query)
//           }
//           is WeatherIntent.SearchCity → {
//               // Set loading, call API, update with results
//               state = state.copy(isLoading = true)
//               val weather = api.getWeather(intent.query)
//               state = state.copy(isLoading = false, temp = weather.temp)
//           }
//           is WeatherIntent.Refresh → {
//               // Reload the current city
//           }
//           is WeatherIntent.Retry → {
//               // Clear error and try again
//           }
//       }
//   }
//
// At T-Mobile:
//   sealed interface CartIntent {
//       data class AddItem(val deviceId: String) : CartIntent
//       data class UpdateQuantity(val itemId: String, val qty: Int) : CartIntent
//       data class ApplyPromo(val code: String) : CartIntent
//       data object RemovePromo : CartIntent
//       data object Checkout : CartIntent
//   }
// =============================================================
