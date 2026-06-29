# 🌦️ Android Weather App

A native Android weather app built with **Kotlin + Jetpack Compose**, demonstrating professional-grade MVI architecture and clean code practices.

> Built as a learning project to master Android patterns used in production apps at companies like T-Mobile.

---

## 📱 Features

- 🔍 **Search any city** — powered by Open-Meteo Geocoding API
- 🌡️ **Live weather data** — temperature, feels like, humidity, wind speed
- ⏱️ **Hourly forecast** — scrollable 24-hour outlook
- 🔄 **°F / °C toggle** — with correct unit conversion on both UI and API level
- 🔃 **Pull-to-refresh** — swipe down to reload current city
- ⚠️ **Error handling** — retry button on network failure
- ✨ **Polished UI** — sky/glass theme with smooth animations

---

## 🏗️ Architecture: MVI + Clean Architecture

```
User tap/type → WeatherIntent → WeatherViewModel
    → WeatherRepository → Retrofit/OkHttp APIs
    → Weather (domain model) → WeatherState → WeatherScreen (Compose UI)
```

| Layer | Files |
|-------|-------|
| **UI** | `WeatherScreen.kt`, `WeatherCard.kt`, `SearchBar.kt`, `HourlyForecast.kt` |
| **MVI** | `WeatherIntent.kt`, `WeatherState.kt`, `WeatherViewModel.kt` |
| **Data** | `WeatherRepositoryImpl.kt`, `WeatherApiService.kt`, DTOs, `WeatherMapper.kt` |
| **Domain** | `domain/model/Weather.kt` |
| **DI** | `di/AppModule.kt` (manual — Hilt coming soon) |

---

## 🛠️ Tech Stack

| Technology | Purpose |
|-----------|---------|
| **Kotlin** | Primary language |
| **Jetpack Compose** | Declarative UI |
| **MVI Pattern** | Unidirectional data flow |
| **Retrofit + OkHttp** | REST API networking |
| **Gson** | JSON parsing |
| **Coroutines + Flow** | Async operations |
| **Open-Meteo API** | Free weather data (no API key needed) |

---

## 🚀 How to Run

1. Clone this repo:
   ```bash
   git clone git@github.com:Ck2108/android-weather-app.git
   ```
2. Open in **Android Studio** (Hedgehog or newer)
3. Select an emulator or connect a device
4. Hit ▶️ **Run**

No API keys needed — Open-Meteo is completely free.

---

## 📂 Project Structure

```
app/src/main/java/com/weather/app/
├── MainActivity.kt
├── data/
│   ├── mapper/WeatherMapper.kt
│   ├── remote/WeatherApiService.kt
│   ├── remote/dto/
│   └── repository/WeatherRepositoryImpl.kt
├── di/AppModule.kt
├── domain/model/Weather.kt
└── ui/
    ├── components/
    │   ├── HourlyForecast.kt
    │   ├── SearchBar.kt
    │   ├── WeatherCard.kt
    │   └── WeatherDetailRow.kt
    ├── theme/
    └── weather/
        ├── WeatherIntent.kt
        ├── WeatherScreen.kt
        ├── WeatherState.kt
        └── WeatherViewModel.kt
```

---

## 📖 What I Learned

- **MVI architecture** — unidirectional data flow keeps UI and logic cleanly separated
- **Clean architecture layers** — UI never talks to the network directly
- **Debugging** — Logcat, breakpoints, and systematic bug hunting
- **Compose** — declarative UI, recomposition, LazyRow, state hoisting
- **Retrofit** — REST APIs, DTOs, mappers from raw JSON to domain models
- **GitHub workflow** — branches, commits, pull requests

---

*Built step-by-step with a mentor — Steps 1–7 complete.*
