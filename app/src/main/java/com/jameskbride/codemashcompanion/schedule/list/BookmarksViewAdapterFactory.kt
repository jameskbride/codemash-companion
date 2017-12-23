package com.jameskbride.codemashcompanion.schedule.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapter
import com.jameskbride.codemashcompanion.sessions.list.SessionsViewAdapterFactory

class BookmarksViewAdapterFactory: SessionsViewAdapterFactory() {

    override fun make(sessionsFragmentPresenter: SessionsFragmentPresenter): SessionsRecyclerViewAdapter {
        return BookmarksRecyclerViewAdapter(sessionsFragmentPresenter)
    }
}