package com.aplimelta.weatherapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplimelta.weatherapp.databinding.ActivityMainBinding
import com.aplimelta.weatherapp.ui.WeatherAdapter
import com.aplimelta.weatherapp.ui.WeatherViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: WeatherAdapter

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WeatherAdapter()

        val dateFormat: DateFormat = SimpleDateFormat("EEEE", Locale.US)
        val calendar = Calendar.getInstance()
        val today = calendar.time
        val todayAsString = dateFormat.format(today)
        binding.tvToday.text = getString(R.string.today, todayAsString)

        viewModel.setWeathers("semarang").observe(this) {
            val temp = it.temperature.filter { text ->
                text.isDigit()
            }

            when {
                "rain" in it.description -> {
                    binding.imgIcon.setAnimation(
                        resources.getIdentifier(
                            "lightrain",
                            "raw",
                            this.packageName
                        )
                    )
                }
                "cloud" in it.description -> {
                    binding.imgIcon.setAnimation(
                        resources.getIdentifier(
                            "cloudy",
                            "raw",
                            this.packageName
                        )
                    )
                }
                "sunny" in it.description -> {
                    binding.imgIcon.setAnimation(
                        resources.getIdentifier(
                            "sunny",
                            "raw",
                            this.packageName
                        )
                    )
                }
                else -> {
                    binding.imgIcon.setAnimation(
                        resources.getIdentifier(
                            "therainturnedfine",
                            "raw",
                            this.packageName
                        )
                    )
                }
            }

            binding.tvTemp.text = it.temperature
            binding.tvDesc.text = it.description
            binding.tvWind.text = it.wind

            adapter.submitList(it.forecast)
            binding.progressBar.visibility = View.GONE
        }

        binding.recyclerForecast.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        val itemDecoration =
            DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        binding.recyclerForecast.addItemDecoration(itemDecoration)
        binding.recyclerForecast.setHasFixedSize(true)
        binding.recyclerForecast.recycledViewPool.clear()
        binding.recyclerForecast.adapter = adapter
    }
}