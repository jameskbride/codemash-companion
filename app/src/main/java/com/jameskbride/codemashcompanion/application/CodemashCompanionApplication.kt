package com.jameskbride.codemashcompanion.application

import android.app.Application
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.injection.ApplicationComponent
import com.jameskbride.codemashcompanion.injection.ApplicationModule
import com.jameskbride.codemashcompanion.injection.DaggerApplicationComponent
import com.jameskbride.codemashcompanion.network.service.CodemashService
import com.jameskbride.codemashcompanion.utils.CrashlyticsFactory
import com.jameskbride.codemashcompanion.utils.FabricWrapper
import javax.inject.Inject

class CodemashCompanionApplication
constructor(
        val applicationComponentFactory: ApplicationComponentFactory = ApplicationComponentFactory(),
        val fabricWrapper: FabricWrapper = FabricWrapper(), val crashlyticsFactory: CrashlyticsFactory = CrashlyticsFactory()) : Application() {

    @Inject
    lateinit var codemashService: CodemashService

    @Inject
    lateinit var conferenceRepository: ConferenceRepository

    override fun onCreate() {
        super.onCreate()

        configure()
        fabricWrapper.with(this, crashlyticsFactory.make())
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