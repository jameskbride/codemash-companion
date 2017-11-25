package com.jameskbride.codemashcompanion.injection

import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(codemashCompanionApplication: CodemashCompanionApplication)
}