package com.jameskbride.codemashcompanion.splash

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import javax.inject.Inject

class SplashActivityImpl @Inject constructor(val presenter: SplashActivityPresenter): SplashActivityView {

    fun onCreate(savedInstanceState: Bundle?, activity: SplashActivity) {
        activity.setContentView(R.layout.activity_splash)
        presenter.view = this
        presenter.requestConferenceData()
    }

    override fun navigateToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}