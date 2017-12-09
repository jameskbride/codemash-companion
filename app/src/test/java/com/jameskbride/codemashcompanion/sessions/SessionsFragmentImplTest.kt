package com.jameskbride.codemashcompanion.sessions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class SessionsFragmentImplTest {

    private lateinit var sessionsFragment: SessionsFragment
    private lateinit var sessionsFragmentPresenter: SessionsFragmentPresenter
    private lateinit var view:View
    private lateinit var layoutInflater:LayoutInflater
    private lateinit var container:ViewGroup
    private lateinit var subject:SessionsFragmentImpl

    @Before
    fun setUp() {
        sessionsFragment = mock()
        sessionsFragmentPresenter = mock()
        view = mock()
        layoutInflater = mock()
        container = mock()

        whenever(layoutInflater.inflate(R.layout.fragment_sessions, container)).thenReturn(view)

        subject = SessionsFragmentImpl(sessionsFragmentPresenter)
    }

    @Test
    fun onCreateViewInflatesTheView() {
        val result = subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        assertSame(view, result)
    }

    @Test
    fun onResumeRequestsTheSessions() {
        subject.onCreateView(layoutInflater, container, null, sessionsFragment)

        subject.onResume()

        verify(sessionsFragmentPresenter).requestSessions()
    }
}