package com.jameskbride.codemashcompanion

import android.os.Bundle

class MainActivityImpl(val presenter: MainActivityPresenter = MainActivityPresenter()) {
    fun onCreate(savedInstanceState: Bundle?, activity: MainActivity) {
        activity.setContentView(R.layout.activity_main)
    }

    fun onResume(mainActivity: MainActivity) {
        presenter.open()
    }

}