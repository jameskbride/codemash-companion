package com.jameskbride.codemashcompanion.sessions.list

import android.content.Context
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.xwray.groupie.Group
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class SessionsRecyclerViewAdapterImplTest {

    @Mock private lateinit var qtn: SessionsRecyclerViewAdapter
    @Mock private lateinit var layoutInflaterFactory: LayoutInflaterFactory
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var context:Context
    @Mock private lateinit var sessionsFragmentPresenter: SessionsFragmentPresenter
    @Mock private lateinit var sessionToListItemConverter: SessionToListItemConverter

    private lateinit var subject: SessionsRecyclerViewAdapterImpl

    @Before
    fun setUp() {
        initMocks(this)
        subject = SessionsRecyclerViewAdapterImpl(sessionsFragmentPresenter, layoutInflaterFactory, sessionToListItemConverter)

        whenever(container.context).thenReturn(context)
    }

    @Test
    fun itAddsAllSessionsWhenTheSessionsAreSet() {
        val sessions = SessionData()

        val groups = listOf<Group>()
        whenever(sessionToListItemConverter.populateSessionList(sessions.groupSessionsByDate()) { session: FullSession -> })
                .thenReturn(groups)

        subject.setSessions(sessions, qtn)

        verify(qtn).addAll(groups)
    }
}