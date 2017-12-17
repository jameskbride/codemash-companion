package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import java.io.Serializable

class SessionDetailActivityImpl {

    fun onCreate(savedInstanceState: Bundle?, sessionDetailActivity: SessionDetailActivity) {
        sessionDetailActivity.setContentView(R.layout.activity_session_detail)
    }

    class SessionDetailParam(val session: FullSession): Serializable

    companion object {
        val PARAMETER_BLOCK:String = "PARAMETER_BLOCK"
    }
}