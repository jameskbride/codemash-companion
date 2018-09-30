package com.jameskbride.codemashcompanion.schedule.list

import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.EmptyViewHolder
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapterImpl
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory

class ScheduleRecyclerViewAdapterImpl constructor(
        override val sessionsFragmentPresenter: SessionsFragmentPresenter,
        override val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory())
    : SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter, layoutInflaterFactory) {

    override fun buildEmptyViewHolder(parent: ViewGroup): EmptyViewHolder {
        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.empty_schedule, parent!!)
        return EmptyViewHolder(view!!)
    }
}