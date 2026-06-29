package com.weather.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.app.ui.theme.GlassWhite
import com.weather.app.ui.theme.TextOnSky
import com.weather.app.ui.theme.TextOnSkyMuted

data class HourlyWeather(
    val time: String,
    val temperature: String,
    val emoji: String
)

@Composable
fun HourlyForecast(
    hourlyData: List<HourlyWeather>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Hourly Forecast",
            style = MaterialTheme.typography.titleMedium,
            color = TextOnSky,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            itemsIndexed(
                items = hourlyData,
                key = { _, hour -> hour.time }
            ) { index, hour ->
                HourlyCard(
                    time = hour.time,
                    temperature = hour.temperature,
                    emoji = hour.emoji,
                    animationDelayIndex = index
                )
            }
        }
    }
}

@Composable
private fun HourlyCard(
    time: String,
    temperature: String,
    emoji: String,
    animationDelayIndex: Int
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(time) {
        alpha.snapTo(0f)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, delayMillis = animationDelayIndex * 50)
        )
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = GlassWhite,
        modifier = Modifier.alpha(alpha.value)
    ) {
        Column(
            modifier = Modifier
                .width(72.dp)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.labelSmall,
                color = TextOnSkyMuted
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = emoji, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = temperature,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextOnSky
            )
        }
    }
}
