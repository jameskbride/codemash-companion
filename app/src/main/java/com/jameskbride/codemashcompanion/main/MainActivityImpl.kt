package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import com.jameskbride.codemashcompanion.R

class MainActivityImpl(val presenter: MainActivityPresenter = MainActivityPresenter()) {
    fun onCreate(savedInstanceState: Bundle?, activity: MainActivity) {
        activity.setContentView(R.layout.activity_main)
    }

    fun onResume(mainActivity: MainActivity) {
        presenter.open()
    }

    fun onPause(mainActivity: MainActivity) {
        presenter.close()
    }

}