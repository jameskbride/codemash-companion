package com.jameskbride.codemashcompanion.splash

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.error.ErrorDialogFactory
import com.jameskbride.codemashcompanion.error.ErrorDialogParams
import com.jameskbride.codemashcompanion.error.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.main.MainActivity
import com.jameskbride.codemashcompanion.utils.BundleFactory
import com.jameskbride.codemashcompanion.utils.IntentFactory
import javax.inject.Inject

class SplashActivityImpl @Inject constructor(
        val presenter: SplashActivityPresenter,
        val intentFactory: IntentFactory = IntentFactory(),
        val errorDialogFactory: ErrorDialogFactory = ErrorDialogFactory(),
        val bundleFactory: BundleFactory = BundleFactory()): SplashActivityView {

    lateinit var activity: SplashActivity

    fun onCreate(savedInstanceState: Bundle?, activity: SplashActivity) {
        this.activity = activity
        activity.setContentView(R.layout.activity_splash)
        presenter.view = this
        presenter.open()
        presenter.requestConferenceData()
    }

    override fun navigateToMain() {
        val intent = intentFactory.make(activity, MainActivity::class.java)

        activity.startActivity(intent)
    }

    override fun showErrorDialog() {
        val bundle = bundleFactory.make()
        bundle.putSerializable(PARAMETER_BLOCK, ErrorDialogParams(title = R.string.oops, message = R.string.no_data_message))

        val errorDialog = errorDialogFactory.make()
        errorDialog.arguments = bundle
        errorDialog.isCancelable = false
        errorDialog.show(activity.supportFragmentManager, "ErrorDialog")
    }

    fun onResume(splashActivity: SplashActivity) {
        presenter.open()
    }

    fun onPause(splashActivity: SplashActivity) {
        presenter.close()
    }
}