package com.jameskbride.codemashcompanion.sessions

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.framework.BaseActivityImpl.Companion.PARAMETER_BLOCK
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivity
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivityImpl
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailParam
import com.jameskbride.codemashcompanion.sessions.list.SessionData
import com.jameskbride.codemashcompanion.sessions.list.SessionsRecyclerViewAdapter
import com.jameskbride.codemashcompanion.sessions.list.SessionsViewAdapterFactory
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.Toaster
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionsFragmentImplTest {

    @Mock private lateinit var qtn: SessionsFragment
    @Mock private lateinit var presenter: SessionsFragmentPresenter
    @Mock private lateinit var view:View
    @Mock private lateinit var layoutInflater:LayoutInflater
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var sessionsView:RecyclerView
    @Mock private lateinit var sessionsRefresh:SwipeRefreshLayout
    @Mock private lateinit var sessionsViewAdapter: SessionsRecyclerViewAdapter
    @Mock private lateinit var sessionsViewAdapterFactory: SessionsViewAdapterFactory
    @Mock private lateinit var linearLayoutManager:LinearLayoutManager
    @Mock private lateinit var intentFactory:IntentFactory
    @Mock private lateinit var context:Context
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var activity:AppCompatActivity
    @Mock private lateinit var toaster: Toaster

    private lateinit var subject:SessionsFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)
        whenever(layoutInflater.inflate(R.layout.fragment_sessions, container, false)).thenReturn(view)
        whenever(sessionsViewAdapterFactory.make(presenter)).thenReturn(sessionsViewAdapter)
        whenever(view.findViewById<RecyclerView>(R.id.sessions)).thenReturn(sessionsView)
        whenever(view.findViewById<SwipeRefreshLayout>(R.id.sessions_refresh)).thenReturn(sessionsRefresh)
        whenever(qtn.activity).thenReturn(activity)

        subject = SessionsFragmentImpl(presenter, sessionsViewAdapterFactory, intentFactory, toaster)
    }

    @Test
    fun onCreateViewInflatesTheView() {
        val result = subject.onCreateView(layoutInflater, container, null, qtn)

        assertSame(view, result)
    }

    @Test
    fun onCreateViewSetsTheSwipeToRefreshListener() {
        subject.onCreateView(layoutInflater, container, null, qtn)

        val swipeListenerCaptor = argumentCaptor<SwipeRefreshLayout.OnRefreshListener>()

        verify(sessionsRefresh).setOnRefreshListener(swipeListenerCaptor.capture())

        swipeListenerCaptor.firstValue.onRefresh()

        verify(presenter).refreshConferenceData()
        verify(sessionsRefresh).setRefreshing(true)
    }

    @Test
    fun itSetsTheSessionsViewAdapterOnCreate() {
        subject.onCreateView(layoutInflater, container, null, qtn)

        verify(sessionsView).setAdapter(sessionsViewAdapter)
    }

    @Test
    fun itConfiguresTheViewForASmoothScrollingGridview() {
        whenever(qtn.makeLinearLayoutManager()).thenReturn(linearLayoutManager)

        subject.onCreateView(layoutInflater, container, null, qtn)

        verify(sessionsView).setLayoutManager(linearLayoutManager)
        verify(sessionsView).setItemViewCacheSize(20)
    }

    @Test
    fun onResumeOpensThePresenter() {
        subject.onCreateView(layoutInflater, container, null, qtn)
        subject.onResume()

        verify(presenter).open()
    }

    @Test
    fun onResumeRequestsTheSessions() {
        subject.onCreateView(layoutInflater, container, null, qtn)

        subject.onResume()

        verify(presenter).requestSessions()
    }

    @Test
    fun onPauseClosesThePresenter() {
        subject.onCreateView(layoutInflater, container, null, qtn)
        subject.onPause()

        verify(presenter).close()
    }

    @Test
    fun onSessionDataReceivedSetsTheSessionsOnTheAdapter() {
        var sessionData = SessionData()

        subject.onCreateView(layoutInflater, container, null, qtn)

        subject.onSessionDataRetrieved(sessionData)

        verify(sessionsViewAdapter).setSessions(sessionData)
        verify(sessionsRefresh).setRefreshing(false)
    }

    @Test
    fun itCanDisplayAnErrorMessage() {
        whenever(toaster.makeText(activity, R.string.could_not_refresh, Toast.LENGTH_SHORT)).thenReturn(toaster)
        subject.onCreateView(layoutInflater, container, null, qtn)

        subject.displayErrorMessage(R.string.could_not_refresh)

        verify(toaster).show()
    }

    @Test
    fun itCanStopRefreshing() {
        subject.onCreateView(layoutInflater, container, null, qtn)

        subject.stopRefreshing()

        verify(sessionsRefresh).setRefreshing(false)
    }

    @Test
    fun itCanNavigateToTheSessionDetail() {
        val session = FullSession()
        whenever(qtn.getContext()).thenReturn(context)
        whenever(intentFactory.make(eq(context), eq(SessionDetailActivity::class.java))).thenReturn(intent)
        subject.onCreateView(layoutInflater, container, null, qtn)

        subject.navigateToSessionDetail(session)

        val extraCaptor = argumentCaptor<SessionDetailParam>()

        verify(intent).putExtra(eq(PARAMETER_BLOCK), extraCaptor.capture())
        val speakerDetailParams = extraCaptor.firstValue
        assertEquals(session.Id, speakerDetailParams.sessionId)

        verify(activity).startActivity(intent)
    }
}