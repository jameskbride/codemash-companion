package com.jameskbride.codemashcompanion.injection

import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(codemashCompanionApplication: CodemashCompanionApplication)
}