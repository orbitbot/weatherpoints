package xyz.weatherpoints.weatherpoints.di.component

import dagger.Component
import xyz.weatherpoints.weatherpoints.di.module.DomainModule
import xyz.weatherpoints.weatherpoints.ui.MapsActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [(DomainModule::class)])
interface DomainInjector {

    fun inject(mapsActivity: MapsActivity)

    @Component.Builder
    interface Builder {
        fun build(): DomainInjector
        fun domainModule(domainModule: DomainModule): Builder
    }
}