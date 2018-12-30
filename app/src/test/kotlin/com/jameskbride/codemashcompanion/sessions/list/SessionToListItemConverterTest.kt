package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat

class SessionToListItemConverterTest {

    private lateinit var subject: SessionToListItemConverter

    private val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)

    @Before
    fun setUp() {
        subject = SessionToListItemConverter()

        buildDefaultSessionData()
    }

    private lateinit var sessionData: SessionData
    private val firstStartTime = "2018-01-10T09:15:00"
    private val firstDate = dateFormatter.parse(firstStartTime)
    private val firstSession = FullSession(SessionStartTime = firstStartTime)
    private val secondStartTime = "2018-01-11T10:15:01"
    private val secondDate = dateFormatter.parse(secondStartTime)
    private val secondSession = FullSession(SessionStartTime = secondStartTime)
    private val thirdSession = FullSession(SessionStartTime = secondStartTime)

    private fun buildDefaultSessionData() {
        var sessions:Array<FullSession> = arrayOf(
                firstSession,
                secondSession,
                thirdSession
        )

        sessionData = SessionData(sessions)
    }


    @Test
    fun itConvertsSessionsToListItems() {
        val result = subject.populateSessionList(sessionData.groupSessionsByDate()) {sessionData: FullSession -> }

        assertEquals(7, result.size)

        val firstDateHeader: DateHeaderListItem = result[0] as DateHeaderListItem
        assertEquals("1/10/2018", firstDateHeader.text)

        val firstTimeHeader: TimeHeaderListItem = result[1] as TimeHeaderListItem
        assertEquals(firstDate, firstTimeHeader.sessionTime)

        val firstItem: SessionListItem = result[2] as SessionListItem
        assertEquals(firstSession, firstItem.session)

        val secondDateHeader: DateHeaderListItem = result[3] as DateHeaderListItem
        assertEquals("1/11/2018", secondDateHeader.text)

        val secondTimeHeader: TimeHeaderListItem = result[4] as TimeHeaderListItem
        assertEquals(secondDate, secondTimeHeader.sessionTime)

        val secondItem: SessionListItem = result[5] as SessionListItem
        assertEquals(secondSession, secondItem.session)

        val thirdItem: SessionListItem = result[6] as SessionListItem
        assertEquals(thirdSession, thirdItem.session)
    }
}
