# Weather App — Build Progress

## Step 1: Project Setup & Fix Gradle ✅
- [x] Create `settings.gradle.kts` — Project table of contents
- [x] Create `build.gradle.kts` (project) — Tool list
- [x] Create `gradle.properties` — Oven settings
- [x] Create `local.properties` — SDK location
- [x] Create `gradle/wrapper/gradle-wrapper.properties` — Gradle version
- [x] Create `.gitignore` — Files Git should ignore
- [x] Create `app/build.gradle.kts` — App recipe with dependencies
- [x] Create `app/proguard-rules.pro` — Code shrinking rules (empty for now)
- [x] Create `AndroidManifest.xml` — App's building permit
- [x] Create `res/values/strings.xml` — Text resources
- [x] Create `res/values/themes.xml` — XML theme for startup
- [x] Create `MainActivity.kt` — The front door
- [x] Create `ui/theme/Color.kt` — Paint palette
- [x] Create `ui/theme/Type.kt` — Font styles
- [x] Create `ui/theme/Theme.kt` — Interior designer (combines colors + fonts)
- [x] Generate Gradle wrapper (gradlew + JAR) — Fixed the missing piece!
- [x] Verify project builds successfully — BUILD SUCCESSFUL ✅
- [x] Verify app runs on emulator showing "Hello Weather! 🌤️" ✅

## Step 2: Build the Weather Screen UI ✅
- [x] Create SearchBar composable
- [x] Create WeatherCard composable
- [x] Create WeatherDetailRow composable (Wind, Humidity, Feels Like)
- [x] Create HourlyForecast composable (LazyRow)
- [x] Create WeatherScreen (assembles all components)
- [x] Update MainActivity to show WeatherScreen
- [x] Build & run — looks beautiful! 🎉

## Step 3: MVI Architecture (Vending Machine) ✅
- [x] Create WeatherState (the display) — single data class for entire screen
- [x] Create WeatherIntent (the buttons) — sealed interface for all user actions
- [x] Create WeatherViewModel (the brain) — processes intents → produces new state
- [x] Update WeatherScreen to observe ViewModel state
- [x] Update SearchBar with onSearch callback for keyboard
- [x] Test with fake data — loading state → weather displays! 🎉

## Step 4: Real Weather Data ✅
- [x] Add Retrofit, OkHttp, Gson dependencies
- [x] Create DTOs (WeatherResponseDto, GeocodingResponseDto)
- [x] Create API service interfaces (WeatherApiService, GeocodingApiService)
- [x] Create Weather domain model
- [x] Create WeatherMapper (DTO → domain, weather codes → emojis)
- [x] Create WeatherRepository interface + implementation
- [x] Create AppModule (manual DI)
- [x] Update ViewModel to use real Repository
- [x] Update WeatherScreen with ViewModel Factory
- [x] Test with REAL data — San Francisco shows 58°F Partly Cloudy! 🎉

## Step 5: Make It Beautiful ✅
- [x] Polish colors and typography
- [x] Add animations
- [x] Add weather icons
- [x] Match reference design (sky/glass theme — no Figma file provided)

## Step 6: Debugging ✅
- [x] Add WeatherLogger + Logcat tags (`WeatherApp`)
- [x] Add debug logs in ViewModel + Repository
- [x] Plant 3 intentional bugs (see `debug/Step6BugHunt.kt`)
- [x] Fix Bug #1 (trailing space search) — `trim()` in ViewModel
- [x] Fix Bug #2 (no spinner on 2nd search) — `isLoading` in WeatherScreen
- [x] Fix Bug #3 (wind unit) — `windUnitSuffix` + API `windSpeedUnit`
- [x] Bonus: emoji fix in WeatherCard.kt, `PROJECT_CONTEXT.md` for IDE handoff

## Step 7: GitHub Workflow ✅
- [x] Fix: removed accidental git from home folder (`rm -rf ~/.git`)
- [x] Initialize git inside Weather App (`git init`)
- [x] Expand `.gitignore` with Android standard entries
- [x] First commit — 39 files, 3182 lines of code
- [x] Generate SSH key pair (`id_ed25519`)
- [x] Add SSH key to GitHub → Settings → SSH and GPG keys
- [x] Switch remote from HTTPS to SSH (`git remote set-url`)
- [x] First push to GitHub (`git push -u origin main`)
- [x] Verified live at: https://github.com/Ck2108/android-weather-app
- [ ] Practice: make a change → commit → push (daily workflow loop)
- [ ] Learn: create a branch → commit → push branch
- [ ] Learn: pull request concept
