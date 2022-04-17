package com.aplimelta.weatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(

	@Json(name="temperature")
	val temperature: String,

	@Json(name="description")
	val description: String,

	@Json(name="forecast")
	val forecast: List<ForecastItem>,

	@Json(name="wind")
	val wind: String
)