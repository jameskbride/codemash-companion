package com.jameskbride.codemashcompanion.sessions

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionsFragmentImplTest {

    @Mock private lateinit var sessionsFragment: SessionsFragment
    @Mock private lateinit var sessionsFragmentPresenter: SessionsFragmentPresenter
    @Mock private lateinit var view:View
    @Mock private lateinit var layoutInflater:LayoutInflater
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var sessionsView:RecyclerView
    @Mock private lateinit var sessionsViewAdapter:SessionsRecyclerViewAdapter
    @Mock private lateinit var sessionsViewAdapterFactory:SessionsViewAdapterFactory
    @Mock private lateinit var linearLayoutManager:LinearLayoutManager

    private lateinit var subject:SessionsFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)
        whenever(layoutInflater.inflate(R.layout.fragment_sessions, container, false)).thenReturn(view)
        whenever(sessionsViewAdapterFactory.make(sessionsFragmentPresenter)).thenReturn(sessionsViewAdapter)
        whenever(view.findViewById<RecyclerView>(R.id.sessions)).thenReturn(sessionsView)

        subject = SessionsFragmentImpl(sessionsFragmentPresenter, sessionsViewAdapterFactory)
    }

    @Test
    fun onCreateViewInflatesTheView() {
        val result = subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        assertSame(view, result)
    }

    @Test
    fun itSetsTheSessionsViewAdapterOnCreate() {
        subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        verify(sessionsView).setAdapter(sessionsViewAdapter)
    }

    @Test
    fun itConfiguresTheViewForASmoothScrollingGridview() {
        whenever(sessionsFragment.makeLinearLayoutManager()).thenReturn(linearLayoutManager)

        subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        verify(sessionsView).setLayoutManager(linearLayoutManager)
        verify(sessionsView).setItemViewCacheSize(20)
    }

    @Test
    fun onResumeRequestsTheSessions() {
        subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        subject.onResume()

        verify(sessionsFragmentPresenter).requestSessions()
    }

    @Test
    fun onSessionDataReceivedSetsTheSessionsOnTheAdapter() {
        var sessionData = SessionData()

        subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        subject.onSessionDataRetrieved(sessionData)

        verify(sessionsViewAdapter).setSessions(sessionData)
    }
}