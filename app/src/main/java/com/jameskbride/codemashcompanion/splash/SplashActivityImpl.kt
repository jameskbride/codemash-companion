package com.jameskbride.codemashcompanion.splash

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.main.MainActivity
import com.jameskbride.codemashcompanion.utils.IntentFactory
import javax.inject.Inject

class SplashActivityImpl @Inject constructor(val presenter: SplashActivityPresenter, val intentFactory: IntentFactory): SplashActivityView {
    lateinit var activity: SplashActivity

    fun onCreate(savedInstanceState: Bundle?, activity: SplashActivity) {
        this.activity = activity
        activity.setContentView(R.layout.activity_splash)
        presenter.view = this
        presenter.requestConferenceData()
    }

    override fun navigateToMain() {
        val intent = intentFactory.make(activity, MainActivity::class.java)

        activity.startActivity(intent)
    }

    override fun showErrorDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onResume(splashActivity: SplashActivity) {
        presenter.open()
    }

    fun onPause(splashActivity: SplashActivity) {
        presenter.close()
    }
}