package xyz.weatherpoints.weatherpoints

import android.app.Application
import timber.log.Timber

class WeatherPointApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}