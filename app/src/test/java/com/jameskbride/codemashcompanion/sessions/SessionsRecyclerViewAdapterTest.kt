package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.network.Session
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import java.util.*

class SessionsRecyclerViewAdapterTest {

    private lateinit var subject: SessionsRecyclerViewAdapter
    private lateinit var impl: SessionsRecyclerViewAdapterImpl

    @Test
    fun itNotifiesOfChangesWhenTheSessionsAreSet() {
        val qtn = mock<SessionsRecyclerViewAdapter>()
        val subject = SessionsRecyclerViewAdapterImpl()

        val sessions = linkedMapOf<Date, Array<Session>>()
        subject.setSessions(sessions, qtn)

        verify(qtn).notifyDataSetChanged()
    }
}