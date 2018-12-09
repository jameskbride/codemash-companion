package com.jameskbride.codemashcompanion.rooms

import android.os.Bundle
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.framework.BaseActivity
import javax.inject.Inject

class RoomActivity: BaseActivity() {

    @Inject
    override lateinit var impl: RoomActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }
}