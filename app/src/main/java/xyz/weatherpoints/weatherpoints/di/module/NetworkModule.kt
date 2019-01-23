package xyz.weatherpoints.weatherpoints.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import xyz.weatherpoints.weatherpoints.network.WeatherApi
import xyz.weatherpoints.weatherpoints.util.BASE_URL_WEATHER

@Suppress("unused")
@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}