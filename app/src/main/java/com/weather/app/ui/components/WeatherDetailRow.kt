package com.weather.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.app.ui.theme.GlassWhite
import com.weather.app.ui.theme.TextOnSky
import com.weather.app.ui.theme.TextOnSkyMuted

@Composable
fun WeatherDetailRow(
    windSpeed: String,
    humidity: String,
    feelsLike: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DetailChip(
            emoji = "💨",
            label = "Wind",
            value = windSpeed,
            modifier = Modifier.weight(1f)
        )
        DetailChip(
            emoji = "💧",
            label = "Humidity",
            value = humidity,
            modifier = Modifier.weight(1f)
        )
        DetailChip(
            emoji = "🌡️",
            label = "Feels Like",
            value = feelsLike,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DetailChip(
    emoji: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = GlassWhite
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 20.sp)

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextOnSkyMuted
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextOnSky
            )
        }
    }
}
