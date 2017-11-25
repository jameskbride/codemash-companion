package com.jameskbride.codemashcompanion.splash

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import javax.inject.Inject

class SplashActivityImpl @Inject constructor(val presenter: SplashActivityPresenter) {

    fun onCreate(savedInstanceState: Bundle?, activity: SplashActivity) {
        activity.setContentView(R.layout.activity_main)
        presenter.requestConferenceData()
    }
}