package com.jameskbride.codemashcompanion.application

import android.app.Application
import com.jameskbride.codemashcompanion.injection.ApplicationComponent
import com.jameskbride.codemashcompanion.injection.ApplicationModule
import com.jameskbride.codemashcompanion.injection.DaggerApplicationComponent
import com.jameskbride.codemashcompanion.network.service.CodemashService
import javax.inject.Inject

class CodemashCompanionApplication : Application() {

    @Inject
    lateinit var codemashService: CodemashService

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule())
                .build()

        applicationComponent.inject(this)
        codemashService.open()
    }

    companion object {
        //platformStatic allow access it from java code
        lateinit var applicationComponent: ApplicationComponent
    }
}