package com.weather.app.ui.weather


// =============================================================
// WeatherViewModel.kt — "The Brain" (Updated for Step 4: REAL DATA!)
// =============================================================
// WHAT CHANGED FROM STEP 3:
//
//   BEFORE: loadWeatherForCity() used FAKE data with a delay(1000)
//   AFTER:  loadWeatherForCity() calls the REAL Repository which
//           fetches data from the Open-Meteo API over the internet!
//
// NEW CONCEPTS:
//
//   "Constructor parameter" — The ViewModel now RECEIVES a repository
//     class WeatherViewModel(private val repository: WeatherRepository)
//     This is Dependency Injection! The ViewModel doesn't create the
//     repository — it receives it from outside. Like a chef receiving
//     ingredients instead of growing them.
//
//   "ViewModelProvider.Factory" — HOW to create a ViewModel with parameters
//     Normally, Compose creates ViewModels with viewModel() which calls
//     the no-argument constructor. But our ViewModel needs a Repository!
//     The Factory tells Compose: "Here's how to build this ViewModel."
//
//   "Result.onSuccess / onFailure" — Handle success/failure elegantly
//     val result = repository.getWeather("Chicago")
//     result.onSuccess { weather -> /* use the data */ }
//     result.onFailure { error -> /* show error message */ }
// =============================================================

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.weather.app.data.mapper.formatHourlyTime
import com.weather.app.data.mapper.weatherCodeToEmoji
import com.weather.app.data.repository.WeatherRepository
import com.weather.app.di.AppModule
import com.weather.app.ui.components.HourlyWeather
import com.weather.app.util.WeatherLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherViewModel(
    private val repository: WeatherRepository  // The supplier — injected from outside!
) : ViewModel() {

    // State — same as before (private writable, public read-only)
    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    // Track the last searched city so we can re-fetch when toggling units
    // This is PRIVATE — only the ViewModel uses it, not the UI
    private var lastSearchedQuery: String = "San Francisco"

    // Load default city on startup
    init {
        loadWeatherForCity("San Francisco")
    }

    // Process user intents — same as before!
    // THE MVI PATTERN DIDN'T CHANGE. Only the data source changed.
    // That's the beauty of clean architecture!
    fun processIntent(intent: WeatherIntent) {
        WeatherLogger.logIntent(intent)
        when (intent) {
            is WeatherIntent.UpdateSearchQuery -> {
                _state.update { it.copy(searchQuery = intent.query) }
            }
            is WeatherIntent.SearchCity -> {
                // 🐛 STEP 6 BUG #1 — Search fails if user types "Chicago " (trailing space).
                // FIX: trim() the query before searching. Find this via Logcat!
                val city = intent.query.trim()

                lastSearchedQuery = city
                loadWeatherForCity(city)
            }
            is WeatherIntent.Refresh -> {
                if (lastSearchedQuery.isNotEmpty()) {
                    loadWeatherForCity(lastSearchedQuery)
                }
            }
            is WeatherIntent.Retry -> {
                _state.update { it.copy(error = null) }
                if (lastSearchedQuery.isNotEmpty()) {
                    loadWeatherForCity(lastSearchedQuery)
                }
            }

            // NEW! Handle the temperature unit toggle
            // 1. Flip isCelsius (true → false, false → true)
            // 2. Re-fetch weather with the new unit
            //    (The API returns data in whichever unit we ask for)
            is WeatherIntent.ToggleTemperatureUnit -> {
                val newIsCelsius = !_state.value.isCelsius
                _state.update { it.copy(isCelsius = newIsCelsius) }
                if (lastSearchedQuery.isNotEmpty()) {
                    loadWeatherForCity(lastSearchedQuery, isCelsius = newIsCelsius)
                }
            }
        }
        WeatherLogger.logState(_state.value)
    }

    // =============================================================
    // LOAD WEATHER — Now with REAL DATA! 🌐
    // =============================================================
    // The structure is the same as Step 3:
    //   1. Set loading state
    //   2. Fetch data
    //   3. Update state with results OR error
    //
    // The only difference: instead of fake data, we call repository!
    // =============================================================
    private fun loadWeatherForCity(city: String, isCelsius: Boolean = _state.value.isCelsius) {
        viewModelScope.launch {
            WeatherLogger.d("Loading weather for city='$city', isCelsius=$isCelsius")
            // Step 1: Show loading spinner
            _state.update { it.copy(isLoading = true, error = null) }
            WeatherLogger.logState(_state.value)


            // Step 2: Call the repository — now with temperature unit!
            val result = repository.getWeather(city, isCelsius)

            // Step 3: Handle the result
            // Pick the right unit suffix based on isCelsius
            val unitSuffix = if (isCelsius) "°C" else "°F"
            val windUnitSuffix = if (isCelsius) "km/h" else "mph"

            result
                .onSuccess { weather ->
                    WeatherLogger.d("API success → ${weather.cityName}, ${weather.temperature}$unitSuffix")
                    // Convert domain model → UI state
                    val hourlyItems = weather.hourlyTimes
                        .zip(weather.hourlyTemperatures)
                        .zip(weather.hourlyWeatherCodes)
                        .take(8)
                        .map { (timeAndTemp, code) ->
                            HourlyWeather(
                                time = formatHourlyTime(timeAndTemp.first),
                                temperature = "${timeAndTemp.second.roundToInt()}°",
                                emoji = weatherCodeToEmoji(code)
                            )
                        }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            cityName = weather.cityName,
                            // NOW uses the dynamic unit suffix! 🎯
                            temperature = "${weather.temperature.roundToInt()}$unitSuffix",
                            condition = weather.condition,
                            weatherEmoji = weatherCodeToEmoji(weather.weatherCode),
                            highTemp = "${weather.highTemp.roundToInt()}°",
                            lowTemp = "${weather.lowTemp.roundToInt()}°",
                            // 🐛 STEP 6 BUG #3 — Wind always shows "mph" even in °C mode.
                            // FIX: use "km/h" when isCelsius is true. Use a breakpoint here!
                            windSpeed = "${weather.windSpeed.roundToInt()} $windUnitSuffix",
                            humidity = "${weather.humidity}%",
                            feelsLike = "${weather.feelsLike.roundToInt()}$unitSuffix",
                            hourlyForecast = hourlyItems,
                            error = null,
                            isCelsius = isCelsius  // Preserve the unit preference
                        )
                    }
                    WeatherLogger.logState(_state.value)
                }
                .onFailure { error ->
                    WeatherLogger.e("API failed for city='$city'", error)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Something went wrong"
                        )
                    }
                    WeatherLogger.logState(_state.value)
                }
        }
    }

    // =============================================================
    // FACTORY — How to create this ViewModel with a Repository
    // =============================================================
    // Because our ViewModel needs a Repository parameter, we need
    // to tell Compose HOW to create it. This Factory does that.
    //
    // In WeatherScreen: viewModel(factory = WeatherViewModel.Factory)
    //
    // At T-Mobile with Hilt, this factory is generated AUTOMATICALLY.
    // You'd just write @HiltViewModel and @Inject constructor(...).
    // We're doing it manually so you understand what Hilt does for you.
    // =============================================================
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(
                    repository = AppModule.weatherRepository
                ) as T
            }
        }
    }
}
