package com.jameskbride.codemashcompanion.sessions

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivity
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailParam
import com.jameskbride.codemashcompanion.sessions.list.SessionData
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapter
import com.jameskbride.codemashcompanion.sessions.list.SessionsViewAdapterFactory
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster

class SessionsFragmentImpl(val presenter: SessionsFragmentPresenter,
                           val sessionsViewAdapterFactory: SessionsViewAdapterFactory = SessionsViewAdapterFactory(),
                           val intentFactory: IntentFactory = IntentFactory(), val toaster: Toaster = Toaster()): SessionsFragmentView {
    private lateinit var sessionsView: RecyclerView

    private lateinit var sessionsViewAdapter: SessionsRecyclerViewAdapter

    private lateinit var qtn: SessionsFragment
    private lateinit var sessionsRefresh: SwipeRefreshLayout
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, qtn: SessionsFragment): View? {
        this.qtn = qtn
        val view = inflater.inflate(R.layout.fragment_sessions, container, false)

        presenter.view = this
        sessionsView = view!!.findViewById(R.id.sessions)
        sessionsView.layoutManager = qtn.makeLinearLayoutManager()
        sessionsView.setItemViewCacheSize(20)
        sessionsViewAdapter = sessionsViewAdapterFactory.make(presenter)
        sessionsView.adapter = sessionsViewAdapter

        sessionsRefresh = view.findViewById(R.id.sessions_refresh)
        sessionsRefresh.setOnRefreshListener {
            sessionsRefresh.isRefreshing = true
            presenter.refreshConferenceData()
        }

        return view
    }

    override fun stopRefreshing() {
        sessionsRefresh.isRefreshing = false
    }

    override fun displayErrorMessage(message: Int) {
        toaster.makeText(qtn.activity!!, R.string.could_not_refresh, Toast.LENGTH_SHORT).show()
    }

    override fun onSessionDataRetrieved(sessionsData: SessionData) {
        sessionsViewAdapter.setSessions(sessionsData)
        sessionsRefresh.isRefreshing = false
    }

    override fun navigateToSessionDetail(session: FullSession) {
        val intent = intentFactory.make(qtn.context, SessionDetailActivity::class.java)
        intent.putExtra(SpeakerDetailActivityImpl.PARAMETER_BLOCK, SessionDetailParam(sessionId = session.Id))

        qtn.activity!!.startActivity(intent)
    }

    fun onResume() {
        presenter.open()
        presenter.requestSessions()
    }

    fun onPause() {
        presenter.close()
    }
}