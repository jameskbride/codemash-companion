package com.jameskbride.codemashcompanion.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R

class SessionsFragmentImpl(val sessionsFragmentPresenter: SessionsFragmentPresenter) {
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, sessionsFragment: SessionsFragment): View? {
        val view = inflater.inflate(R.layout.fragment_sessions, container)

        return view
    }

    fun onResume() {
        sessionsFragmentPresenter.requestSessions()
    }

    fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}