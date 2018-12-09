package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.framework.BaseActivity
import javax.inject.Inject

class SpeakerDetailActivity: BaseActivity() {

    @Inject
    override lateinit var impl: SpeakerDetailActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}