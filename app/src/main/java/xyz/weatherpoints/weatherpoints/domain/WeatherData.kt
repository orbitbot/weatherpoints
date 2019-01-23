package xyz.weatherpoints.weatherpoints.domain

import io.reactivex.Observable
import xyz.weatherpoints.weatherpoints.network.WeatherApi

class WeatherData(private val weatherApi: WeatherApi) {

    fun getWeatherDataFor(latitude: String, longitude: String) : Observable<String> {
        return weatherApi.getWeatherDataFor(latitude, longitude)
            .map { result ->
                val lines = result.split("\n")
                var temperatureLine = lines.get(lines.indexOfFirst { elem -> elem.contains("temperature") }).trim()

                return@map temperatureLine
            }
    }
}