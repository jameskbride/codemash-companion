package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter

open class SessionsViewAdapterFactory {
    open fun make(sessionsFragmentPresenter: SessionsFragmentPresenter): SessionsRecyclerViewAdapter {
        return SessionsRecyclerViewAdapter(sessionsFragmentPresenter)
    }
}