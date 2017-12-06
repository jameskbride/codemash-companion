package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SpeakerDetailActivity: AppCompatActivity() {

    @Inject
    lateinit var speakerDetailActivityImpl: SpeakerDetailActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        speakerDetailActivityImpl.onCreate(savedInstanceState, this)
    }
}