package com.jameskbride.codemashcompanion.about

import android.os.Bundle
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.framework.BaseActivity
import javax.inject.Inject

class AboutActivity: BaseActivity() {

    @Inject
    override lateinit var impl:AboutActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}