package com.jameskbride.codemashcompanion.rooms

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import com.jameskbride.codemashcompanion.framework.BaseActivity
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl
import java.io.Serializable

class RoomActivityImpl: BaseActivityImpl() {
    override fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity) {
        qtn.setContentView(R.layout.activity_room)
        configureActionBar(qtn)
        setTitle(qtn, R.string.map)
    }

    override fun onResume(sessionDetailActivity: BaseActivity) {
    }

    override fun onPause(sessionDetailActivity: BaseActivity) {
    }
}

class RoomParams(val rooms:List<ConferenceRoom> = listOf()): Serializable