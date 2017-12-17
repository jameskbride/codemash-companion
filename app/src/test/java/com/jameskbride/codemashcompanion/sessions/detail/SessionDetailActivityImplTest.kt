package com.jameskbride.codemashcompanion.sessions.detail

import com.jameskbride.codemashcompanion.R
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class SessionDetailActivityImplTest {

    private lateinit var qtn: SessionDetailActivity

    private lateinit var subject: SessionDetailActivityImpl

    @Before
    fun setUp() {
        qtn = mock()
        subject = SessionDetailActivityImpl()
    }

    @Test
    fun onCreateItSetsTheContentView() {
        subject.onCreate(null, qtn)

        verify(qtn).setContentView(R.layout.activity_session_detail)
    }
}