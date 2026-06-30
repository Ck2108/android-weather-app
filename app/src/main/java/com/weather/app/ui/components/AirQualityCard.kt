package com.weather.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.app.ui.theme.GlassWhite
import com.weather.app.ui.theme.TextOnSky
import com.weather.app.ui.theme.TextOnSkyMuted

// =============================================================
// AirQualityCard.kt — Full-width AQI display card
// =============================================================
// Inspired by Apple Weather's air quality card.
// Shows the AQI number, label, and a color-coded gradient bar
// with a marker dot showing where the current AQI falls.
// =============================================================

// AQI color: changes from green → yellow → orange → red → purple → maroon
// Updated for US EPA AQI thresholds
private fun aqiColor(index: Int): Color = when {
    index < 0    -> Color(0x80FFFFFF)   // Muted white for "--"
    index <= 50  -> Color(0xFF4CAF50)   // Green — Good
    index <= 100 -> Color(0xFFFFC107)   // Yellow/Amber — Moderate
    index <= 150 -> Color(0xFFFF9800)   // Orange — Unhealthy for Sensitive Groups
    index <= 200 -> Color(0xFFF44336)   // Red — Unhealthy
    index <= 300 -> Color(0xFF9C27B0)   // Purple — Very Unhealthy
    else         -> Color(0xFF4A148C)   // Maroon — Hazardous
}

@Composable
fun AirQualityCard(
    aqiIndex: String,
    aqiLabel: String,
    modifier: Modifier = Modifier
) {
    // Parse raw index for color + bar position (--  → -1 → muted)
    val indexInt = aqiIndex.toIntOrNull() ?: -1
    val accentColor = aqiColor(indexInt)

    // Bar progress: map AQI to 6 visually equal segments to keep the gradient balanced
    val barProgress = if (indexInt < 0) 0f else {
        val segment = 1f / 6f
        when {
            indexInt <= 50  -> (indexInt / 50f) * segment
            indexInt <= 100 -> segment + ((indexInt - 50) / 50f) * segment
            indexInt <= 150 -> 2 * segment + ((indexInt - 100) / 50f) * segment
            indexInt <= 200 -> 3 * segment + ((indexInt - 150) / 50f) * segment
            indexInt <= 300 -> 4 * segment + ((indexInt - 200) / 100f) * segment
            else            -> 5 * segment + ((indexInt.coerceAtMost(500) - 300) / 200f) * segment
        }
    }

    // The gradient colors of the AQI scale bar (evenly spaced for visual balance)
    val gradientColors = listOf(
        Color(0xFF4CAF50),  // Green
        Color(0xFFFFC107),  // Yellow
        Color(0xFFFF9800),  // Orange
        Color(0xFFF44336),  // Red
        Color(0xFF9C27B0),  // Purple
        Color(0xFF4A148C)   // Maroon
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = GlassWhite
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            // ── Header ──────────────────────────────────────────────
            Text(
                text = "🌬️  AIR QUALITY",
                style = MaterialTheme.typography.labelSmall,
                color = TextOnSkyMuted,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // ── Index number + label row ─────────────────────────────
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Big AQI number
                Text(
                    text = aqiIndex,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    lineHeight = 52.sp
                )

                Column(modifier = Modifier.padding(bottom = 6.dp)) {
                    // Label e.g. "Good"
                    Text(
                        text = aqiLabel,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextOnSky
                    )
                    // Sub-label
                    Text(
                        text = "US EPA AQI",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextOnSkyMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ── Gradient progress bar ────────────────────────────────
            // WHY Canvas? Canvas lets us draw custom shapes (rounded bar + dot marker)
            // that aren't possible with standard Compose components.
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            ) {
                val barHeight = size.height
                val cornerRadius = CornerRadius(barHeight / 2, barHeight / 2)

                // Draw the full gradient bar (background)
                drawRoundRect(
                    brush = Brush.horizontalGradient(gradientColors),
                    cornerRadius = cornerRadius
                )

                // Draw the white dot marker at the current AQI position
                if (indexInt >= 0) {
                    val dotX = (size.width * barProgress).coerceIn(
                        barHeight / 2,           // don't go off left edge
                        size.width - barHeight / 2  // don't go off right edge
                    )
                    // Outer white ring
                    drawCircle(
                        color = Color.White,
                        radius = barHeight / 2 + 2.dp.toPx(),
                        center = Offset(dotX, size.height / 2)
                    )
                    // Inner colored fill
                    drawCircle(
                        color = accentColor,
                        radius = barHeight / 2,
                        center = Offset(dotX, size.height / 2)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // ── Scale labels ─────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Good", style = MaterialTheme.typography.labelSmall, color = TextOnSkyMuted)
                Text("Hazardous", style = MaterialTheme.typography.labelSmall, color = TextOnSkyMuted)
            }
        }
    }
}
