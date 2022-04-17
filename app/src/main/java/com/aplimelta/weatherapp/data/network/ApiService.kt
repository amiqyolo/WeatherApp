package com.aplimelta.weatherapp.data.network

import com.aplimelta.weatherapp.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/weather/{city}")
    suspend fun getWeather(
        @Path("city")
        city: String
    ): Response<WeatherResponse>

}