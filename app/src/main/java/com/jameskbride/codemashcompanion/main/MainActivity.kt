package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainActivityImpl: MainActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        mainActivityImpl.onCreate(savedInstanceState, this)
    }

}
