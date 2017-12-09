package com.jameskbride.codemashcompanion.sessions

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.network.Session
import java.util.*

class SessionsRecyclerViewAdapter constructor(
        val sessionsRecyclerViewAdapterImpl: SessionsRecyclerViewAdapterImpl = SessionsRecyclerViewAdapterImpl())
    : RecyclerView.Adapter<SessionViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: SessionViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SessionViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setSessions(sessionData: LinkedHashMap<Date, Array<Session>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SessionsViewAdapterFactory {
    fun make(): SessionsRecyclerViewAdapter {
        return SessionsRecyclerViewAdapter()
    }
}

class SessionsRecyclerViewAdapterImpl

class SessionViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView)