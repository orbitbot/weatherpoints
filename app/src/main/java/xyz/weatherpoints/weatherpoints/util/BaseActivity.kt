package xyz.weatherpoints.weatherpoints.util

import android.support.v7.app.AppCompatActivity
import xyz.weatherpoints.weatherpoints.di.component.DaggerDomainInjector
import xyz.weatherpoints.weatherpoints.di.component.DomainInjector
import xyz.weatherpoints.weatherpoints.di.module.DomainModule
import xyz.weatherpoints.weatherpoints.ui.MapsActivity

abstract class BaseActivity: AppCompatActivity() {
    private val injector: DomainInjector = DaggerDomainInjector.builder().domainModule(DomainModule).build()

    init {
        when (this) {
            is MapsActivity -> injector.inject(this)
        }
    }
}