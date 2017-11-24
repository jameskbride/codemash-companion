package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var impl: MainActivityImpl

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}
