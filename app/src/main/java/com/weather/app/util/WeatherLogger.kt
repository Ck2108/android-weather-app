package com.weather.app.util

// =============================================================
// WeatherLogger.kt — Step 6: Your Logcat "microphone"
// =============================================================
// Logcat = Android Studio's live diary of everything the app says.
//
// HOW TO USE IN ANDROID STUDIO:
//   1. Run the app
//   2. Bottom bar → "Logcat"
//   3. Filter box → type: WeatherApp
//   4. You'll ONLY see our app's debug messages!
//
// LOG LEVELS (least → most serious):
//   Log.d = Debug   → development info (what we're using)
//   Log.i = Info    → normal events worth noting
//   Log.w = Warning → something odd but not broken
//   Log.e = Error   → something failed
//
// At T-Mobile you'll grep Logcat for tags like "CartViewModel"
// when checkout totals look wrong.
// =============================================================

import android.util.Log
import com.weather.app.ui.weather.WeatherIntent
import com.weather.app.ui.weather.WeatherState

object WeatherLogger {

    const val TAG = "WeatherApp"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun i(message: String) {
        Log.i(TAG, message)
    }

    fun w(message: String) {
        Log.w(TAG, message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e(TAG, message, throwable)
        } else {
            Log.e(TAG, message)
        }
    }

    fun logIntent(intent: WeatherIntent) {
        d("INTENT → $intent")
    }

    fun logState(state: WeatherState) {
        d(
            "STATE → city=${state.cityName}, temp=${state.temperature}, " +
                "loading=${state.isLoading}, error=${state.error}, query=${state.searchQuery}"
        )
    }
}
