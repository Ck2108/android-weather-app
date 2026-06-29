package com.weather.app

// =============================================================
// MainActivity.kt — "The Front Door" (Updated for Step 2!)
// =============================================================
// WHAT CHANGED: We replaced the simple "Hello Weather!" text
// with our full WeatherScreen that has a search bar, weather card,
// detail chips, and hourly forecast.
//
// Notice how simple this file is — the Activity just says:
//   "Show the WeatherScreen inside our theme."
// All the complex UI is in WeatherScreen.kt and its components.
//
// 🍽️ Restaurant analogy: The front door (Activity) doesn't cook food.
//    It just opens the door and seats you in the dining room (WeatherScreen).
// =============================================================

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.weather.app.ui.theme.WeatherAppTheme
import com.weather.app.ui.weather.WeatherScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Step 6: If you see this in Logcat, logging works! Filter: WeatherApp
        Log.i("WeatherApp", "✅ APP STARTED — Logcat is working!")
        enableEdgeToEdge()

        // setContent = "Put this on the screen"
        // WeatherAppTheme = Apply our colors and fonts
        // WeatherScreen() = Our full weather UI (from WeatherScreen.kt)
        setContent {
            WeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}
