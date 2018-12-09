package com.jameskbride.codemashcompanion.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var impl: SplashActivityImpl

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }

    override fun onPause() {
        super.onPause()
        impl.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        impl.onResume(this)
    }
}
