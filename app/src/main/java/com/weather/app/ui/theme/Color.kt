package com.weather.app.ui.theme

// =============================================================
// Color.kt — "The Paint Palette"
// =============================================================
// This file lists ALL the colors our app uses.
// Why keep colors in one file? So if you want to change the
// app's look, you change it HERE and it updates EVERYWHERE.
//
// 🎨 Think of it like a painter's palette — all your colors
//    are organized in one place, and you pick from them.
//
// Color(0xFF1E88E5) — What does this mean?
//   0x = "this is a hex number" (a number system computers like)
//   FF = fully visible (not transparent)
//   1E88E5 = the actual color (a shade of blue)
//
// The "80" and "40" suffixes are brightness levels:
//   80 = lighter (for dark theme — light text on dark background)
//   40 = darker  (for light theme — dark text on light background)
// =============================================================

import androidx.compose.ui.graphics.Color

// ----- Colors for DARK theme (light colors on dark background) -----
val Blue80 = Color(0xFFBBDEFB)      // Light blue
val BlueGrey80 = Color(0xFFB0BEC5)  // Light blue-grey
val Cyan80 = Color(0xFF80DEEA)      // Light cyan (sky color)

// ----- Colors for LIGHT theme (dark colors on light background) -----
val Blue40 = Color(0xFF1E88E5)      // Blue (our primary/main color!)
val BlueGrey40 = Color(0xFF546E7A)  // Blue-grey
val Cyan40 = Color(0xFF00ACC1)      // Cyan (accent color)

// ----- Weather screen palette (Step 5) -----
// Centralized so every component uses the same "sky at night" look.
val SkyGradientTop = Color(0xFF1B3A5C)       // Deep twilight blue
val SkyGradientBottom = Color(0xFF0F2030)    // Dark navy
val GlassWhite = Color(0x26FFFFFF)           // 15% white — frosted glass cards
val GlassWhiteStrong = Color(0x33FFFFFF)     // 20% white — buttons & chips
val TextOnSky = Color(0xFFFFFFFF)            // Primary text on dark sky
val TextOnSkyMuted = Color(0xB3FFFFFF)       // 70% white — labels
val TextOnSkyFaint = Color(0x99FFFFFF)       // 60% white — secondary info
val ErrorCoral = Color(0xFFFF6B6B)           // Friendly error red
