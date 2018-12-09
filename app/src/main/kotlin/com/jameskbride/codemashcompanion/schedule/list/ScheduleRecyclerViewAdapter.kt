package com.jameskbride.codemashcompanion.schedule.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapterImpl

class ScheduleRecyclerViewAdapter constructor(sessionsFragmentPresenter: SessionsFragmentPresenter,
                                              override val impl: SessionsRecyclerViewAdapterImpl = ScheduleRecyclerViewAdapterImpl(sessionsFragmentPresenter))
    : SessionsRecyclerViewAdapter(sessionsFragmentPresenter)