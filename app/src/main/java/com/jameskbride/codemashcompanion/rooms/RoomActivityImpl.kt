package com.jameskbride.codemashcompanion.rooms

import android.os.Bundle
import com.github.chrisbanes.photoview.PhotoView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.framework.BaseActivity
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl
import java.io.Serializable
import javax.inject.Inject

class RoomActivityImpl @Inject constructor(): BaseActivityImpl() {
    override fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity) {
        qtn.setContentView(R.layout.activity_room)
        configureActionBar(qtn)
        setTitle(qtn, R.string.map)
        val roomParams = qtn.intent.getSerializableExtra(PARAMETER_BLOCK) as RoomParams
        qtn.findViewById<PhotoView>(R.id.map_view).setImageResource(roomParams.room)
    }

    override fun onResume(sessionDetailActivity: BaseActivity) {
    }

    override fun onPause(sessionDetailActivity: BaseActivity) {
    }
}

class RoomParams(val room: Int): Serializable