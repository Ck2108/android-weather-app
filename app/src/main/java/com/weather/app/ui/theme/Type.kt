package com.weather.app.ui.theme

// =============================================================
// Type.kt — "The Font Styles"
// =============================================================
// This file defines how TEXT looks in our app:
//   - Which font to use
//   - How big different text types should be
//   - How bold or light the text is
//
// "Typography" = The art of arranging text to make it readable
//   and attractive. Think of it like choosing fonts in Google Docs.
//
// Material Design has preset text styles:
//   - displayLarge = HUGE text (like a billboard)
//   - headlineLarge = Section titles
//   - titleLarge = Smaller titles
//   - bodyLarge = Normal reading text (this is what you're reading now)
//   - labelSmall = Tiny labels
//
// We only customize what we want to change from the defaults.
// =============================================================

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Step 5: Full typography scale for the weather screen
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
