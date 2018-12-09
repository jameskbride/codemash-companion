package com.jameskbride.codemashcompanion.codeofconduct

import android.os.Bundle
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.framework.BaseActivity
import javax.inject.Inject

class CodeOfConductActivity: BaseActivity() {
    @Inject
    override lateinit var impl:CodeOfConductActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}