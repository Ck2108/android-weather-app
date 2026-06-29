package com.weather.app.data.remote.dto

// =============================================================
// GeocodingResponseDto.kt — "The Delivery Package (City Search)"
// =============================================================
// When the user types "Chicago", we first need to find Chicago's
// coordinates (latitude/longitude) because the weather API needs
// coordinates, not city names.
//
// So we use a GEOCODING API:
//   "Chicago" → latitude: 41.85, longitude: -87.65
//
// Then we use those coordinates to get weather:
//   lat: 41.85, lon: -87.65 → temperature: 72°F, sunny
//
// It's like ordering food delivery:
//   1. "I want pizza from Mario's" → look up Mario's address (geocoding)
//   2. Use the address to actually send the delivery truck (weather API)
// =============================================================

import com.google.gson.annotations.SerializedName

// The response from the geocoding API
data class GeocodingResponseDto(
    @SerializedName("results") val results: List<GeocodingResultDto>?
    // "?" means this can be NULL (empty).
    // If you search "asdfghjkl" (not a real city), results will be null.
)

// One city result
data class GeocodingResultDto(
    @SerializedName("name") val name: String,           // "Chicago"
    @SerializedName("latitude") val latitude: Double,    // 41.85003
    @SerializedName("longitude") val longitude: Double,  // -87.65005
    @SerializedName("country") val country: String?,     // "United States"
    @SerializedName("admin1") val admin1: String?        // "Illinois" (state/region)
)
