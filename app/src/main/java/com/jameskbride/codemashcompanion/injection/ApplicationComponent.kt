package com.jameskbride.codemashcompanion.injection

import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.main.MainActivity
import com.jameskbride.codemashcompanion.speakers.SpeakersFragment
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivity
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailFragment
import com.jameskbride.codemashcompanion.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(codemashCompanionApplication: CodemashCompanionApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(speakersFragment: SpeakersFragment)
    fun inject(speakerDetailActivity: SpeakerDetailActivity)
    fun inject(speakerDetailFragment: SpeakerDetailFragment)
}