package com.weather.app.debug

// =============================================================
// Step6BugHunt.kt — Intentional bugs for YOU to find & fix!
// =============================================================
// There are 3 hidden bugs in the app right now. Your mission:
//
// 🐛 BUG #1 — "Chicago " (with a space) fails to search
//    WHERE TO LOOK: WeatherViewModel → processIntent → SearchCity
//    TOOL: Logcat — filter "WeatherApp", search "Chicago "
//    HINT: Users often type trailing spaces. What should we do?
//
// 🐛 BUG #2 — Searching a new city doesn't show the loading spinner
//    SYMPTOM: Old weather stays on screen while new city loads
//    WHERE TO LOOK: WeatherScreen → AnimatedContent → ScreenPhase
//    TOOL: Breakpoint on loadWeatherForCity(), watch isLoading + cityName
//    HINT: When is "loading" shown vs hidden?
//
// 🐛 BUG #3 — Wind speed always says "mph" even in °C mode
//    SYMPTOM: Toggle to Celsius → wind still shows "mph"
//    WHERE TO LOOK: WeatherViewModel → loadWeatherForCity → onSuccess
//    TOOL: Breakpoint after API returns, inspect isCelsius vs windSpeed string
//    HINT: Temperature uses unitSuffix — does wind?
//
// BONUS — Repository hides the real error (good Logcat practice!)
//    Turn OFF Wi-Fi → search a city → read Logcat for the REAL exception
//    while the screen shows a friendly message.
//
// BREAKPOINT CHEAT SHEET:
//   1. Click left gutter (line number) in WeatherViewModel.kt
//   2. Run app in Debug mode (🐞 icon, not ▶)
//   3. Trigger the action (search, toggle, etc.)
//   4. App PAUSES — inspect variables in the Debug panel
//   5. Press F9 (Resume) to continue
//
// LAYOUT INSPECTOR CHEAT SHEET:
//   1. Run app → Tools → Layout Inspector
//   2. Select your emulator device + com.weather.app
//   3. Click any UI element to see its Composable name & size
//   4. Use "Recomposition counts" to see what's redrawing too much
// =============================================================
