package com.aplimelta.weatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastItem(

    @field:Json(name = "temperature")
    val temperature: String,

    @field:Json(name = "day")
    val day: String,

    @field:Json(name = "wind")
    val wind: String
)