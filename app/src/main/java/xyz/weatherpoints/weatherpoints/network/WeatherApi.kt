package xyz.weatherpoints.weatherpoints.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/weatherapi/locationforecast/1.9/")
    fun getWeatherDataFor(@Query("lat") latitude: String, @Query("lon") longitude: String): Observable<String>
}