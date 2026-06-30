package com.weather.app.data.remote.dto

// =============================================================
// AirQualityResponseDto.kt — "The Air Quality Delivery Box"
// =============================================================
// The Open-Meteo Air Quality API returns JSON like this:
//   {
//     "current": {
//       "european_aqi": 42
//     }
//   }
//
// European AQI scale:
//   0-20   = Good       (clean air)
//   21-40  = Fair       (acceptable)
//   41-60  = Moderate   (sensitive groups affected)
//   61-80  = Poor       (most people affected)
//   81-100 = Very Poor  (health effects for everyone)
//   100+   = Hazardous  (emergency conditions)
//
// WHY a separate DTO file?
//   Because this comes from a completely different API server
//   (air-quality-api.open-meteo.com vs api.open-meteo.com).
//   Separate server → separate DTO → separate Retrofit instance.
// =============================================================

import com.google.gson.annotations.SerializedName

data class AirQualityResponseDto(
    @SerializedName("current") val current: CurrentAirQualityDto
)

data class CurrentAirQualityDto(
    @SerializedName("us_aqi") val usAqi: Int   // 85 (US EPA scale 0-500)
)
