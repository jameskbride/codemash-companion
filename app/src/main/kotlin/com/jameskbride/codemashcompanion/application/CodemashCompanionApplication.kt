package com.jameskbride.codemashcompanion.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.injection.ApplicationComponent
import com.jameskbride.codemashcompanion.injection.ApplicationModule
import com.jameskbride.codemashcompanion.injection.DaggerApplicationComponent
import com.jameskbride.codemashcompanion.network.service.CodemashService
import javax.inject.Inject

class CodemashCompanionApplication
constructor(val applicationComponentFactory: ApplicationComponentFactory = ApplicationComponentFactory()) : Application() {

    @Inject
    lateinit var codemashService: CodemashService

    @Inject
    lateinit var conferenceRepository: ConferenceRepository

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        configure()
    }

    fun configure() {
        applicationComponent = applicationComponentFactory.build(this)
        applicationComponent.inject(this)

        codemashService.open()
        conferenceRepository.open()
    }

    companion object {
        //platformStatic allow access it from java code
        lateinit var applicationComponent: ApplicationComponent
    }
}

class ApplicationComponentFactory {
    fun build(codemashCompanionApplication: CodemashCompanionApplication): ApplicationComponent {
        return DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(codemashCompanionApplication))
                .build()
    }
}