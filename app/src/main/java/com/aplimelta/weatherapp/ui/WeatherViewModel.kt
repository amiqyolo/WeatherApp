package com.aplimelta.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.aplimelta.weatherapp.data.model.WeatherResponse
import com.aplimelta.weatherapp.data.network.ApiConfig
import okio.IOException

class WeatherViewModel : ViewModel() {

    fun setWeathers(city: String): LiveData<WeatherResponse> = liveData {
        try {
            val response = ApiConfig.retrofit.getWeather(city)
            val result = response.body()

            if (response.isSuccessful) {
                if (result != null) {
                    emit(result)
                }
            }
        } catch (e: IOException) {

        }
    }

}