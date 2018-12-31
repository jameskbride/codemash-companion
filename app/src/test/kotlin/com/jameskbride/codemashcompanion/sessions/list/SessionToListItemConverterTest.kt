package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.listitems.EmptyListItem
import com.xwray.groupie.ExpandableGroup
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
        assertEquals(1, firstDay.groupCount)
    }

    @Test
    fun itCollapsesDaysThatAreInThePast() {
        val result = subject.populateSessionList(sessionData.groupSessionsByDate()) {sessionData: FullSession -> }

        val firstDay = result[0] as ExpandableGroup
        assertFalse(firstDay.isExpanded)
    }

    @Test
    fun itExpandsTheCurrentDate() {
        val currentDate = LocalDateTime.now()
        val currentDateString = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(currentDate)

        val sessionData = SessionData(arrayOf(FullSession(SessionStartTime = currentDateString)))
        val results = subject.populateSessionList(sessionData.groupSessionsByDate(), { })

        val firstDay = results[0] as ExpandableGroup
        assertTrue(firstDay.isExpanded)
    }

    @Test
    fun itExpandsFutureDates() {
        val currentDate = LocalDateTime.now().plusDays(1)
        val currentDateString = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(currentDate)

        val sessionData = SessionData(arrayOf(FullSession(SessionStartTime = currentDateString)))
        val results = subject.populateSessionList(sessionData.groupSessionsByDate(), { })

        val firstDay = results[0] as ExpandableGroup
        assertTrue(firstDay.isExpanded)
    }

    @Test
    fun itCollapsesTimesThatAreInThePast() {
        val result = subject.populateSessionList(sessionData.groupSessionsByDate()) {sessionData: FullSession -> }

        val firstDay = result[0] as ExpandableGroup
        assertFalse(firstDay.isExpanded)
    }
}
