package xyz.weatherpoints.weatherpoints.domain

import android.content.Context
import android.location.Geocoder
import io.reactivex.Observable
import timber.log.Timber
import java.util.*

class LocationData() {

    private val UNKNOWN = "Unknown"

    fun getLocationNameFor(latitude: Double, longitude: Double, context: Context): Observable<String> {
        if (Geocoder.isPresent()) {
            val geocoder = Geocoder(context, Locale.getDefault())

            return Observable.create { result ->
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (addresses.isEmpty()) {
                    result.onNext(UNKNOWN)
                    result.onComplete()
                } else {
                    val address = addresses.first()
                    Timber.d("address result %s", address)
                    result.onNext(String.format("%s", address.countryName))
                    result.onComplete()
                }
            }

        } else {
            // TODO: get location using GeoIP service
            return Observable.just(UNKNOWN)
        }
    }

}