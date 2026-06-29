package com.weather.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.app.ui.theme.GlassWhite
import com.weather.app.ui.theme.TextOnSky
import com.weather.app.ui.theme.TextOnSkyFaint
import com.weather.app.ui.theme.TextOnSkyMuted

@Composable
fun WeatherCard(
    cityName: String,
    temperature: String,
    condition: String,
    weatherEmoji: String,
    highTemp: String,
    lowTemp: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GlassWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineMedium,
                color = TextOnSky
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weatherEmoji.ifBlank { "🌤️" },
                fontSize = 80.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = temperature,
                style = MaterialTheme.typography.displayLarge,
                color = TextOnSky
            )

            Text(
                text = condition,
                style = MaterialTheme.typography.titleMedium,
                color = TextOnSkyMuted
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "H:$highTemp  L:$lowTemp",
                style = MaterialTheme.typography.bodyLarge,
                color = TextOnSkyFaint
            )
        }
    }
}
