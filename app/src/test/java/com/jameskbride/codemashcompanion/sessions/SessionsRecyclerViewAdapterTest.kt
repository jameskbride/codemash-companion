package com.jameskbride.codemashcompanion.sessions

import com.jameskbride.codemashcompanion.network.Session
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.text.SimpleDateFormat
import java.util.*

class SessionsRecyclerViewAdapterTest {

    @Mock private lateinit var qtn: SessionsRecyclerViewAdapter
    private lateinit var subject: SessionsRecyclerViewAdapterImpl

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

    @Before
    fun setUp() {
        initMocks(this)
        subject = SessionsRecyclerViewAdapterImpl()
    }

    @Test
    fun itNotifiesOfChangesWhenTheSessionsAreSet() {
        val sessions = linkedMapOf<Date, Array<Session>>()
        subject.setSessions(sessions, qtn)

        verify(qtn).notifyDataSetChanged()
    }

    @Test
    fun itConvertsSessionsToListItems() {
        var sessionData = linkedMapOf<Date, Array<Session>>()
        val firstStartTime = "2018-01-11T09:15:00"
        val secondStartTime = "2018-01-11T10:15:00"

        val firstDate = dateFormatter.parse(firstStartTime)
        val firstSession = Session(SessionStartTime = firstStartTime)
        sessionData[firstDate] = arrayOf(firstSession)
        val secondDate = dateFormatter.parse(secondStartTime)
        val secondSession = Session(SessionStartTime = secondStartTime)
        val thirdSession = Session(SessionStartTime = secondStartTime)
        sessionData[secondDate] = arrayOf(
                secondSession,
                thirdSession
        )

        subject.setSessions(sessionData, qtn)

        val result = subject.sessionsList
        assertEquals(5, result.size)

        val firstHeader:HeaderListItem = result[0] as HeaderListItem
        assertEquals(firstDate, firstHeader.sessionTime)

        val firstItem:SessionListItem = result[1] as SessionListItem
        assertEquals(firstSession, firstItem.session)

        val secondHeader:HeaderListItem = result[2] as HeaderListItem
        assertEquals(secondDate, secondHeader.sessionTime)

        val secondItem:SessionListItem = result[3] as SessionListItem
        assertEquals(secondSession, secondItem.session)

        val thirdItem:SessionListItem = result[4] as SessionListItem
        assertEquals(thirdSession, thirdItem.session)
    }
}