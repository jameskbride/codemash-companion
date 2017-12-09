package com.jameskbride.codemashcompanion.sessions

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Session
import java.util.*

class SessionsFragmentImpl(val sessionsFragmentPresenter: SessionsFragmentPresenter, val sessionsViewAdapterFactory: SessionsViewAdapterFactory = SessionsViewAdapterFactory()): SessionsFragmentView {
    private lateinit var sessionsView: RecyclerView

    private lateinit var sessionsViewAdapter: SessionsRecyclerViewAdapter

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, qtn: SessionsFragment): View? {
        val view = inflater.inflate(R.layout.fragment_sessions, container)

        sessionsFragmentPresenter.view = this
        sessionsView = view!!.findViewById<RecyclerView>(R.id.sessions)
        sessionsView.layoutManager = qtn.makeLinearLayoutManager()
        sessionsView.setItemViewCacheSize(20)
        sessionsViewAdapter = sessionsViewAdapterFactory.make()
        sessionsView.adapter = sessionsViewAdapter
        
        return view
    }

    fun onResume() {
        sessionsFragmentPresenter.requestSessions()
    }

    fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSessionDataRetrieved(sessionsData: LinkedHashMap<Date, Array<Session>?>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}