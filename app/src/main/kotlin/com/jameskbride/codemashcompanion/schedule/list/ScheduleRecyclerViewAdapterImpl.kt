package com.jameskbride.codemashcompanion.schedule.list

import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapterImpl
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory

class ScheduleRecyclerViewAdapterImpl constructor(
        override val sessionsFragmentPresenter: SessionsFragmentPresenter,
        override val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory())
    : SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter, layoutInflaterFactory) {
}