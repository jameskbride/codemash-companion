package com.jameskbride.codemashcompanion

import android.app.Application
import com.jameskbride.codemashcompanion.injection.ApplicationComponent
import com.jameskbride.codemashcompanion.injection.ApplicationModule
import com.jameskbride.codemashcompanion.injection.DaggerApplicationComponent

class CodemashCompanionApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {
        //platformStatic allow access it from java code
        lateinit var applicationComponent: ApplicationComponent
    }
}