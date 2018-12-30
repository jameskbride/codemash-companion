package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import org.junit.Assert.*
import org.junit.Test

class SessionDataTest {

    private val firstStartTime = "2018-01-10T09:15:00"
    private val firstSession = FullSession(SessionStartTime = firstStartTime)
    private val secondStartTime = "2018-01-11T10:15:01"
    private val secondSession = FullSession(SessionStartTime = secondStartTime)

    @Test
    fun itSortsSessionsByDate() {
        val subject = SessionData(arrayOf(secondSession, firstSession))

        val results = subject.groupSessionsByDate()

        assertTrue(results[0].sessions.contains(firstSession))
        assertTrue(results[1].sessions.contains(secondSession))
    }
}