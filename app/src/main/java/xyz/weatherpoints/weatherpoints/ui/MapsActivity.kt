package xyz.weatherpoints.weatherpoints.ui

import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.weatherpoints.weatherpoints.R
import xyz.weatherpoints.weatherpoints.domain.LocationData
import xyz.weatherpoints.weatherpoints.domain.WeatherData
import xyz.weatherpoints.weatherpoints.util.BaseActivity
import javax.inject.Inject

class MapsActivity : BaseActivity() {

    private lateinit var mMap: GoogleMap
    private lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var weatherData: WeatherData

    @Inject
    lateinit var locationData: LocationData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        // If Google Play services is not installed on the device, the user will be prompted to install
        // it inside the SupportMapFragment. This method will only be triggered once the user has
        // installed Google Play services and returned to the app.
        //
        // race condition wrt compositeDisposable? :)
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                mMap = googleMap
                mMap.setOnMapLongClickListener { latLng ->
                    Timber.d("Clicked map - %s", latLng)

                    val lat = String.format("%.2f", latLng.latitude)
                    val lon = String.format("%.2f", latLng.longitude)

                    compositeDisposable.add(
                        locationData.getLocationNameFor(latLng.latitude, latLng.longitude, this@MapsActivity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ next ->
                                Timber.d("got location %s", next)
                                Toast.makeText(this@MapsActivity, next, Toast.LENGTH_SHORT).show()
                            }, { error ->
                                Timber.e(error)
                            })
                    )

                    compositeDisposable.add(
                        weatherData.getWeatherDataFor(lat, lon)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ next ->
                                Timber.d("got temperature %s", next)
                                Toast.makeText(this@MapsActivity, next, Toast.LENGTH_SHORT).show()
                            }, { error ->
                                Timber.e(error)
                            })
                    )
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
}
