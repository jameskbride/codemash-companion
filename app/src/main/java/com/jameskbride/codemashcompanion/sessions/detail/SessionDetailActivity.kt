package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.framework.BaseActivity
import javax.inject.Inject

class SessionDetailActivity: BaseActivity() {

    @Inject override
    lateinit var impl:SessionDetailActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}