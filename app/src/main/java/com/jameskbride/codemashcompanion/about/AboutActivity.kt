package com.jameskbride.codemashcompanion.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class AboutActivity: AppCompatActivity() {

    @Inject
    lateinit var impl:AboutActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}