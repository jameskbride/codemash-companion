package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.xwray.groupie.GroupAdapter

open class SessionsRecyclerViewAdapter constructor(val sessionsFragmentPresenter: SessionsFragmentPresenter,
                                                   open val impl: SessionsRecyclerViewAdapterImpl = SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter))
    : GroupAdapter<SessionViewHolder>() {

    fun setSessions(sessionData: SessionData) {
        impl.setSessions(sessionData, this)
    }
}