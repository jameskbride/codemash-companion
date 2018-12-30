package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionsByDateTest {

    private val firstStartTime = "2018-01-11T09:15:00"
    private val firstSession = FullSession(SessionStartTime = firstStartTime)
    private val secondStartTime = "2018-01-11T10:15:01"
    private val secondSession = FullSession(SessionStartTime = secondStartTime)

    @Test
    fun itSortsSessionsByTime() {
        val subject = SessionsByDate(firstStartTime, listOf(secondSession, firstSession))

        val results = subject.sessionsByTime()

        assertEquals(2, results.size)
        assertTrue(results[0].sessions.contains(firstSession))
        assertTrue(results[1].sessions.contains(secondSession))
    }
}