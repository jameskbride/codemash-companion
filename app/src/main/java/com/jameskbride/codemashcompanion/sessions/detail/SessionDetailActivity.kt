package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SessionDetailActivity: AppCompatActivity() {

    val impl = SessionDetailActivityImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        impl.onCreate(savedInstanceState, this)
    }
}