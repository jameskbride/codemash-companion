package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import javax.inject.Inject

class MainActivityImpl @Inject constructor(val presenter: MainActivityPresenter) {

    fun onCreate(savedInstanceState: Bundle?, activity: MainActivity) {
        activity.setContentView(R.layout.activity_main)
    }

    fun onResume(mainActivity: MainActivity) {
//        presenter.open()
    }

    fun onPause(mainActivity: MainActivity) {
//        presenter.close()
    }

}