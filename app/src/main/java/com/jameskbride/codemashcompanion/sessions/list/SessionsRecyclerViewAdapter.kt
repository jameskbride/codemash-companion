package com.jameskbride.codemashcompanion.sessions.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter

open class SessionsRecyclerViewAdapter constructor(val sessionsFragmentPresenter: SessionsFragmentPresenter,
                                                   open val impl: SessionsRecyclerViewAdapterImpl = SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter))
    : RecyclerView.Adapter<SessionViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return impl.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: SessionViewHolder?, position: Int) {
        impl.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return impl.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SessionViewHolder {
        return impl.onCreateViewHolder(parent, viewType)
    }

    fun setSessions(sessionData: SessionData) {
        impl.setSessions(sessionData, this)
    }
}