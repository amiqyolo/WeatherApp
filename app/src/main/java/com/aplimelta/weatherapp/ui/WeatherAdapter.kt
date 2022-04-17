package com.aplimelta.weatherapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aplimelta.weatherapp.data.model.ForecastItem
import com.aplimelta.weatherapp.databinding.ItemForecastBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter :
    ListAdapter<ForecastItem, WeatherAdapter.WeatherViewHolder>(WeatherAdapterComparator()) {

    class WeatherViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ForecastItem) {
            val dateFormat: DateFormat = SimpleDateFormat("EEEE", Locale.US)
            val calendar = Calendar.getInstance()
            val today = calendar.time

            for (i in data.day.iterator()) {
                calendar.add(Calendar.DAY_OF_YEAR, i.code+1)

                val tomorrow = calendar.time
                val tomorrowAsString = dateFormat.format(tomorrow)

                binding.tvDays.text = tomorrowAsString
            }

            binding.tvItemTemp.text = data.temperature
            binding.tvItemWind.text = data.wind

            var wind = data.wind.filter {
                it.isDigit()
            }.toDoubleOrNull()

            var temp = data.temperature.filter {
                it.isDigit()
            }.toDoubleOrNull()

            var result = 0.0
            if (wind != null && temp != null) {
                result = temp - (wind * 0.7)
            } else {
                wind = 0.0
                temp = 0.0
                temp.minus(wind * 0.7)
            }

            try {
                when (result) {
                    in 0.0..25.0 -> {
                        binding.lottieItemIcon.setAnimation(
                            itemView.context.resources.getIdentifier(
                                "lightrain",
                                "raw",
                                itemView.context.packageName
                            )
                        )
                    }
                    in 25.1..31.0 -> {
                        binding.lottieItemIcon.setAnimation(
                            itemView.context.resources.getIdentifier(
                                "cloudy",
                                "raw",
                                itemView.context.packageName
                            )
                        )
                    }
                    in 31.1..100.0 -> {
                        binding.lottieItemIcon.setAnimation(
                            itemView.context.resources.getIdentifier(
                                "sunny",
                                "raw",
                                itemView.context.packageName
                            )
                        )
                    }
                }
            } catch (e: NumberFormatException) {
                Log.e("Adapter", "bind: $e")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        with(holder) {
            val data = getItem(position)
            bind(data)
        }
    }

    class WeatherAdapterComparator : DiffUtil.ItemCallback<ForecastItem>() {
        override fun areItemsTheSame(
            oldItem: ForecastItem,
            newItem: ForecastItem,
        ): Boolean = oldItem.day == newItem.day
                && oldItem.wind == newItem.wind
                && oldItem.temperature == newItem.temperature

        override fun areContentsTheSame(
            oldItem: ForecastItem,
            newItem: ForecastItem,
        ): Boolean =
            oldItem == newItem
    }

}