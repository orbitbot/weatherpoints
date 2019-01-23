package xyz.weatherpoints.weatherpoints.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import xyz.weatherpoints.weatherpoints.domain.LocationData
import xyz.weatherpoints.weatherpoints.domain.WeatherData
import xyz.weatherpoints.weatherpoints.network.WeatherApi

@Suppress("unused")
@Module(
    includes = [NetworkModule::class]
)
object DomainModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideWeatherData(weatherApi: WeatherApi): WeatherData {
        return WeatherData(weatherApi)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLocationData(): LocationData {
        return LocationData()
    }
}