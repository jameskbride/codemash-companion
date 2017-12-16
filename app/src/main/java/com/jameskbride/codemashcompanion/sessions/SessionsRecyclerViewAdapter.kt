package com.jameskbride.codemashcompanion.sessions

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class SessionsRecyclerViewAdapter constructor(val sessionsFragmentPresenter: SessionsFragmentPresenter,
        val impl: SessionsRecyclerViewAdapterImpl = SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter))
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

class SessionsViewAdapterFactory {
    fun make(sessionsFragmentPresenter: SessionsFragmentPresenter): SessionsRecyclerViewAdapter {
        return SessionsRecyclerViewAdapter(sessionsFragmentPresenter)
    }
}