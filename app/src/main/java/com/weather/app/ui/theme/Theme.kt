package com.weather.app.ui.theme

// =============================================================
// Theme.kt — "The Interior Designer"
// =============================================================
// This file combines our Colors + Typography into a complete
// THEME that the entire app uses.
//
// 🍽️ Restaurant analogy: Color.kt is the paint samples,
//    Type.kt is the font choices, and THIS file is the
//    interior designer who puts it all together and says
//    "OK, the walls are blue, menus use this font, done!"
//
// KEY CONCEPT: MaterialTheme
//   Google created Material Design — a design system with rules
//   for how apps should look. Material3 is the latest version.
//   Instead of picking colors for every button individually,
//   you set up a theme ONCE and all buttons automatically
//   use the right colors. It's like setting a dress code.
//
// WHAT IS "DARK THEME"?
//   When someone turns on "Dark Mode" on their phone, our app
//   automatically switches from light colors to dark colors.
//   We don't need to do anything special — the theme handles it!
// =============================================================

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// ----- Dark Theme Colors -----
// Used when the phone is in "Dark Mode"
private val DarkColorScheme = darkColorScheme(
    primary = Blue80,       // Main color (buttons, highlighted text)
    secondary = BlueGrey80, // Supporting color
    tertiary = Cyan80       // Accent color for extra emphasis
)

// ----- Light Theme Colors -----
// Used when the phone is in normal "Light Mode"
private val LightColorScheme = lightColorScheme(
    primary = Blue40,       // Main color
    secondary = BlueGrey40, // Supporting color
    tertiary = Cyan40       // Accent color
)

// =============================================================
// WeatherAppTheme — The function that APPLIES our theme
// =============================================================
// This wraps our entire app. Any Composable inside it
// automatically gets our colors and fonts.
//
// Usage (in MainActivity.kt):
//   WeatherAppTheme {
//       // Everything in here uses our theme!
//       Text("Hello") // This text uses our theme's colors
//   }
// =============================================================
@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),  // Auto-detect dark mode
    dynamicColor: Boolean = false,                // Off — weather screen uses its own sky palette
    content: @Composable () -> Unit               // The UI to wrap with our theme
) {
    // Decide which colors to use:
    val colorScheme = when {
        // Android 12+ can use "Dynamic Color" — colors that match the
        // user's wallpaper. Super cool feature!
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // If no dynamic color, use our manually defined colors
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Apply the theme! Everything inside "content" gets these styles.
    MaterialTheme(
        colorScheme = colorScheme,   // Our colors
        typography = Typography,      // Our fonts (from Type.kt)
        content = content            // The actual UI
    )
}
