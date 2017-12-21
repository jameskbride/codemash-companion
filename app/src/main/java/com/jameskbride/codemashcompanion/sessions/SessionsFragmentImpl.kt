package com.jameskbride.codemashcompanion.sessions

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivity
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailParam
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.utils.IntentFactory

class SessionsFragmentImpl(val presenter: SessionsFragmentPresenter,
                           val sessionsViewAdapterFactory: SessionsViewAdapterFactory = SessionsViewAdapterFactory(),
                           val intentFactory: IntentFactory = IntentFactory()): SessionsFragmentView {
    private lateinit var sessionsView: RecyclerView
    private lateinit var sessionsViewAdapter: SessionsRecyclerViewAdapter
    private lateinit var qtn: SessionsFragment

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, qtn: SessionsFragment): View? {
        this.qtn = qtn
        val view = inflater.inflate(R.layout.fragment_sessions, container, false)

        presenter.view = this
        sessionsView = view!!.findViewById(R.id.sessions)
        sessionsView.layoutManager = qtn.makeLinearLayoutManager()
        sessionsView.setItemViewCacheSize(20)
        sessionsViewAdapter = sessionsViewAdapterFactory.make(presenter)
        sessionsView.adapter = sessionsViewAdapter

        view.findViewById<SwipeRefreshLayout>(R.id.sessions_refresh).setOnRefreshListener { presenter.refreshConferenceData() }

        return view
    }

    fun onResume() {
        presenter.requestSessions()
    }

    fun onPause() {
    }

    override fun onSessionDataRetrieved(sessionsData: SessionData) {
        sessionsViewAdapter.setSessions(sessionsData)
    }

    override fun navigateToSessionDetail(session: FullSession) {
        val intent = intentFactory.make(qtn.context, SessionDetailActivity::class.java)
        intent.putExtra(SpeakerDetailActivityImpl.PARAMETER_BLOCK, SessionDetailParam(session))

        qtn.activity!!.startActivity(intent)
    }
}