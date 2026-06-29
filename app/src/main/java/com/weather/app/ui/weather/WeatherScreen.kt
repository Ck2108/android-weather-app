package com.weather.app.ui.weather

// =============================================================
// WeatherScreen.kt — "The Screen" (Step 5: Polished UI!)
// =============================================================
// STEP 5 ADDITIONS:
//   - Pull-to-refresh (swipe down to reload weather)
//   - Retry button when something goes wrong
//   - Fade-in animations when weather appears
//   - Centralized colors & typography from theme
// =============================================================

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.app.ui.components.AirQualityCard
import com.weather.app.ui.components.HourlyForecast
import com.weather.app.ui.components.SearchBar
import com.weather.app.ui.components.SunriseSunsetRow
import com.weather.app.ui.components.WeatherCard
import com.weather.app.ui.components.WeatherDetailRow
import com.weather.app.ui.theme.ErrorCoral
import com.weather.app.ui.theme.GlassWhiteStrong
import com.weather.app.ui.theme.SkyGradientBottom
import com.weather.app.ui.theme.SkyGradientTop
import com.weather.app.ui.theme.TextOnSky
import com.weather.app.ui.theme.TextOnSkyMuted

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Only show pull-to-refresh spinner when reloading an existing city
    // (not on the very first load when the screen is empty)
    val isRefreshing = state.isLoading && state.cityName.isNotEmpty()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.processIntent(WeatherIntent.Refresh) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(SkyGradientTop, SkyGradientBottom)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        modifier = Modifier.clickable {
                            viewModel.processIntent(WeatherIntent.ToggleTemperatureUnit)
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = GlassWhiteStrong
                    ) {
                        Text(
                            text = if (state.isCelsius) "°C" else "°F",
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            color = TextOnSky,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = { newText ->
                        viewModel.processIntent(WeatherIntent.UpdateSearchQuery(newText))
                    },
                    onSearch = {
                        if (state.searchQuery.isNotBlank()) {
                            viewModel.processIntent(WeatherIntent.SearchCity(state.searchQuery))
                        }
                    }
                )

                AnimatedContent(
                    targetState = when {
                        // 🐛 STEP 6 BUG #2 — Spinner only shows on FIRST load (empty cityName).
                        // When searching a 2nd city, old weather stays visible during loading.
                        // FIX: show loading whenever isLoading is true.
                        state.isLoading -> ScreenPhase.InitialLoading
                        state.error != null -> ScreenPhase.Error
                        state.cityName.isNotEmpty() -> ScreenPhase.Success
                        else -> ScreenPhase.Empty
                    },
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(200))
                    },
                    label = "weather_screen_phase"
                ) { phase ->
                    when (phase) {
                        ScreenPhase.InitialLoading -> LoadingContent()
                        ScreenPhase.Error -> ErrorContent(
                            message = state.error ?: "Something went wrong",
                            onRetry = { viewModel.processIntent(WeatherIntent.Retry) }
                        )
                        ScreenPhase.Success -> SuccessContent(state = state)
                        ScreenPhase.Empty -> Box(modifier = Modifier.height(1.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = TextOnSky
            )
        }
    }
}

private enum class ScreenPhase {
    InitialLoading, Error, Success, Empty
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = TextOnSky,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "😕", fontSize = 48.sp)

        Text(
            text = message,
            color = ErrorCoral,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = GlassWhiteStrong,
                contentColor = TextOnSky
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Try Again", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun SuccessContent(state: WeatherState) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(400)) + scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(400)
        )
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            WeatherCard(
                cityName = state.cityName,
                temperature = state.temperature,
                condition = state.condition,
                weatherEmoji = state.weatherEmoji,
                highTemp = state.highTemp,
                lowTemp = state.lowTemp
            )

            WeatherDetailRow(
                windSpeed = state.windSpeed,
                humidity = state.humidity,
                feelsLike = state.feelsLike,
            )

            if (state.hourlyForecast.isNotEmpty()) {
                HourlyForecast(hourlyData = state.hourlyForecast)
            }

            AirQualityCard(
                aqiIndex = state.aqiIndex,
                aqiLabel = state.aqiLabel
            )

            SunriseSunsetRow(
                sunrise = state.sunrise,
                sunset = state.sunset
            )
        }
    }
}
