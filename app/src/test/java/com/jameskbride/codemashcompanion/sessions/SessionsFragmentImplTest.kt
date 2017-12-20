package com.jameskbride.codemashcompanion.sessions

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivity
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivityImpl
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailParam
import com.jameskbride.codemashcompanion.utils.IntentFactory
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

    @Mock private lateinit var sessionsFragment: SessionsFragment
    @Mock private lateinit var sessionsFragmentPresenter: SessionsFragmentPresenter
    @Mock private lateinit var view:View
    @Mock private lateinit var layoutInflater:LayoutInflater
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var sessionsView:RecyclerView
    @Mock private lateinit var sessionsViewAdapter:SessionsRecyclerViewAdapter
    @Mock private lateinit var sessionsViewAdapterFactory:SessionsViewAdapterFactory
    @Mock private lateinit var linearLayoutManager:LinearLayoutManager
    @Mock private lateinit var intentFactory:IntentFactory
    @Mock private lateinit var context:Context
    @Mock private lateinit var intent:Intent
    @Mock private lateinit var activity:AppCompatActivity

    private lateinit var subject:SessionsFragmentImpl

    @Before
    fun setUp() {
        initMocks(this)
        whenever(layoutInflater.inflate(R.layout.fragment_sessions, container, false)).thenReturn(view)
        whenever(sessionsViewAdapterFactory.make(sessionsFragmentPresenter)).thenReturn(sessionsViewAdapter)
        whenever(view.findViewById<RecyclerView>(R.id.sessions)).thenReturn(sessionsView)
        whenever(sessionsFragment.activity).thenReturn(activity)

        subject = SessionsFragmentImpl(sessionsFragmentPresenter, sessionsViewAdapterFactory, intentFactory)
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

    @Test
    fun itCanNavigateToTheSessionDetail() {
        val session = FullSession()
        whenever(sessionsFragment.getContext()).thenReturn(context)
        whenever(intentFactory.make(eq(context), eq(SessionDetailActivity::class.java))).thenReturn(intent)
        subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        subject.navigateToSessionDetail(session)

        val extraCaptor = argumentCaptor<SessionDetailParam>()

        verify(intent).putExtra(eq(SessionDetailActivityImpl.PARAMETER_BLOCK), extraCaptor.capture())
        val speakerDetailParams = extraCaptor.firstValue
        assertEquals(session, speakerDetailParams.session)

        verify(activity).startActivity(intent)
    }
}