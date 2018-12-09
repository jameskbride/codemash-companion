package com.jameskbride.codemashcompanion.schedule.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapter
import com.jameskbride.codemashcompanion.sessions.list.SessionsViewAdapterFactory

class ScheduleViewAdapterFactory : SessionsViewAdapterFactory() {

    override fun make(sessionsFragmentPresenter: SessionsFragmentPresenter): SessionsRecyclerViewAdapter {
        return ScheduleRecyclerViewAdapter(sessionsFragmentPresenter)
    }
}