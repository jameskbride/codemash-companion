package com.jameskbride.codemashcompanion.schedule.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapterImpl

class BookmarksRecyclerViewAdapter constructor(sessionsFragmentPresenter: SessionsFragmentPresenter,
                                               override val impl: SessionsRecyclerViewAdapterImpl = BookmarksRecyclerViewAdapterImpl(sessionsFragmentPresenter))
    : SessionsRecyclerViewAdapter(sessionsFragmentPresenter)