package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.listitems.EmptyListItem
import com.xwray.groupie.ExpandableGroup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SessionToListItemConverterTest {

    private lateinit var subject: SessionToListItemConverter

    @Before
    fun setUp() {
        subject = SessionToListItemConverter()

        buildDefaultSessionData()
    }

    private lateinit var sessionData: SessionData
    private val firstStartTime = "2018-01-10T09:15:00"
    private val firstSession = FullSession(SessionStartTime = firstStartTime)
    private val secondStartTime = "2018-01-11T10:15:01"
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
    fun givenNoSessionsItGeneratesAtLeastTheEmptyListItem() {
        val result = subject.populateSessionList(listOf(), { sessionData: FullSession -> })

        assertEquals(1, result.size)
        assertTrue(result[0] is EmptyListItem)
    }

    @Test
    fun itCreatesAnExpandableGroupForEachDay() {
        val result = subject.populateSessionList(sessionData.groupSessionsByDate()) {sessionData: FullSession -> }

        assertEquals(2, result.size)
    }

    @Test
    fun itCreatesAnExpandableGroupForEachTime() {
        val result = subject.populateSessionList(sessionData.groupSessionsByDate()) {sessionData: FullSession -> }

        val firstDay = result[0] as ExpandableGroup
        assertEquals(1, firstDay.groupCount - 1)
    }
}
