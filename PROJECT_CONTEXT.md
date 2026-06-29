# Weather App — Project Context (for AI / new IDE handoff)

> **Paste this to your AI assistant in Antigravity (or any IDE):**
> *"Read `PROJECT_CONTEXT.md`, `task tracker.md`, and `implementation_plan.md` before helping me."*

---

## What this app is

Native Android weather app built in **Kotlin + Jetpack Compose** to learn professional Android patterns (MVI, clean architecture, debugging). Fetches live weather from **Open-Meteo** (free, no API key).

**Package:** `com.weather.app`  
**Default city on launch:** San Francisco

---

## Architecture (MVI + Clean Architecture)

```
User tap/type → WeatherIntent → WeatherViewModel.processIntent()
    → WeatherRepository → Retrofit/OkHttp APIs
    → Weather (domain model) → WeatherState → WeatherScreen (Compose UI)
```

| Layer | Key files |
|-------|-----------|
| **UI** | `ui/weather/WeatherScreen.kt`, `ui/components/*` |
| **MVI** | `WeatherIntent.kt`, `WeatherState.kt`, `WeatherViewModel.kt` |
| **Data** | `WeatherRepositoryImpl.kt`, `WeatherApiService.kt`, DTOs, `WeatherMapper.kt` |
| **Domain** | `domain/model/Weather.kt` |
| **DI** | `di/AppModule.kt` (manual — no Hilt yet) |
| **Debug** | `util/WeatherLogger.kt` (Logcat tag: `WeatherApp`) |

**MVI rules:**
- **Intent** = user action (search, refresh, toggle °F/°C, retry)
- **State** = single immutable snapshot of entire screen
- **ViewModel** = only place that processes intents and updates state
- **Screen** = dumb UI; observes `state`, sends `processIntent()`

---

## Build progress (Steps 1–6 COMPLETE)

| Step | Status | Summary |
|------|--------|---------|
| 1 | ✅ | Gradle, project setup |
| 2 | ✅ | Compose UI (SearchBar, WeatherCard, HourlyForecast, etc.) |
| 3 | ✅ | MVI (Intent, State, ViewModel) |
| 4 | ✅ | Retrofit + OkHttp + Repository + live API data |
| 5 | ✅ | Polished sky/glass theme, animations, pull-to-refresh, retry button |
| 6 | ✅ | Debugging practice — 3 intentional bugs **found and fixed by student** |

---

## Step 6 bugs — ALL FIXED

| Bug | Problem | Fix location |
|-----|---------|--------------|
| **#1** | Search `"Chicago "` (trailing space) failed | `WeatherViewModel` → `intent.query.trim()` |
| **#2** | No loading spinner on 2nd city search | `WeatherScreen` → `state.isLoading -> InitialLoading` (first in `when`) |
| **#3** | Wind showed `mph` in °C mode | `WeatherViewModel` → `windUnitSuffix`; `WeatherRepositoryImpl` → `windSpeedUnit = "kmh"` on API call |

**Lesson learned:** ViewModel suffix = UI label only. Repository API param = real data unit from server.

---

## Other fixes applied

- **Emoji missing on main card:** Removed broken `Animatable` scale animation in `WeatherCard.kt` (was preventing ☀️ from rendering).
- **Gradle sandbox errors in Cursor:** Use `GRADLE_USER_HOME=$HOME/.gradle` or build from Android Studio. Path with `cursor-sandbox-cache` is NOT an app bug.
- **`gradle.properties`:** `org.gradle.java.home` points to Android Studio JBR (Java 21).
- **`run-emulator.sh`:** Script to start emulator, build, install, launch.

---

## How to run

**Android Studio:** Open this folder → select emulator → ▶ Run

**Terminal:**
```bash
cd "/Users/chinmayeekale/Desktop/Weather App"
./run-emulator.sh
```

**Logcat filter:** `WeatherApp`

**Debug:** Use 🐞 Debug mode for breakpoints in `WeatherViewModel.kt`

---

## APIs used

1. **Geocoding:** `https://geocoding-api.open-meteo.com/v1/search?name={city}`
2. **Weather:** `https://api.open-meteo.com/v1/forecast?latitude=...&longitude=...`
   - `temperature_unit`: `celsius` | `fahrenheit`
   - `wind_speed_unit`: `kmh` | `mph`

---

## Student goals (T-Mobile prep)

1. ✅ MVI pattern — Intent / State / ViewModel flow
2. ✅ Debugging — Logcat, stack traces, breakpoints
3. Compose UI — pixel-perfect from Figma (no Figma file yet; sky/glass theme done)
4. 🔜 GitHub — push repo, pull, branch, team workflow
5. 🔜 Optional next: Hilt DI, unit tests, Room caching

---

## Key files to read first

1. `task tracker.md` — checkbox progress
2. `implementation_plan.md` — learning guide with analogies
3. `debug/Step6BugHunt.kt` — debugging exercise notes
4. `ui/weather/WeatherViewModel.kt` — app brain
5. `ui/weather/WeatherScreen.kt` — UI + MVI wiring

---

## Mentor style requested

- Explain **why**, not just what
- Use simple analogies (vending machine for MVI, restaurant for clean architecture)
- Don't overwhelm — one step at a time
- Student fixed all Step 6 bugs themselves with guidance

---

*Last updated: June 2026 — after Step 6 completion + emoji fix + fresh emulator build.*
