package com.jameskbride.codemashcompanion.data

import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.jameskbride.codemashcompanion.data.model.*
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class ConferenceDaoTest {

    private lateinit var database: ConferenceDatabase

    private lateinit var conferenceDao: ConferenceDao

    private lateinit var testScheduler:TestScheduler

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, ConferenceDatabase::class.java!!).build()
        conferenceDao = database.conferenceDao()

        testScheduler = TestScheduler()
    }

    @Test
    fun itCanRetrieveSessionSpeakersFromAFullSpeaker() {
        val sessionId = "1"
        val speakerId = "1"
        setupSessionSpeakers(sessionId, speakerId)

        var speakerResults: Array<FullSpeaker> = getSpeakersById(speakerId)

        assertEquals(1, speakerResults.size)
        assertEquals(1, speakerResults[0].sessionSpeakers.size)
    }

    @Test
    fun itCanRetrieveSessionSpeakersFromAFullSpeakerBySessionId() {
        val sessionId = "1"
        val speakerId = "1"
        setupSessionSpeakers(sessionId, speakerId)

        var speakerResults: Array<FullSpeaker> = getSpeakersBySessionId(sessionId)

        assertEquals(1, speakerResults.size)
        assertEquals(1, speakerResults[0].sessionSpeakers.size)
    }

    @Test
    fun itCanRetrieveSessionSpeakersIndirectlyOnFullSpeakersFromFullSessions() {
        val sessionId = "1"
        val speakerId = "1"
        setupSessionSpeakers(sessionId, speakerId)

        var sessionResults: Array<FullSession> = getSessionsById(sessionId)

        assertEquals(1, sessionResults.size)

        var speakerResults: Array<FullSpeaker> = getSpeakersBySessionId(sessionId)

        assertEquals(1, speakerResults.size)
        assertEquals(1, speakerResults[0].sessionSpeakers.size)
    }

    @Test
    fun itCanRetrieveBookmarkedSessions() {
        val sessionId = "1"
        insertBookmark(sessionId)

        var sessionResults: Array<FullSession> = getBookmarkedSession()

        assertEquals(1, sessionResults.size)
        assertEquals(sessionId, sessionResults[0].Id)
    }

    private fun getBookmarkedSession(): Array<FullSession> {
        var sessionResults: Array<FullSession> = arrayOf()
        val sessionsResponse = conferenceDao.getBookmarkedSessions()
        sessionsResponse
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> sessionResults = result }
        testScheduler.triggerActions()
        return sessionResults
    }

    private fun insertBookmark(sessionId: String) {
        conferenceDao.insertAll(arrayOf(Session(Id = sessionId)))

        val bookmark = Bookmark(sessionId = sessionId)
        conferenceDao.insert(bookmark)
    }

    private fun getSpeakersById(speakerId: String): Array<FullSpeaker> {
        val response = conferenceDao.getSpeakers(arrayOf(speakerId))

        var speakerResults: Array<FullSpeaker> = arrayOf()
        response
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> speakerResults = result }
        testScheduler.triggerActions()
        return speakerResults
    }

    private fun setupSessionSpeakers(sessionId: String, speakerId: String) {
        val session = Session(Id = sessionId)
        val speaker = Speaker(Id = speakerId)

        conferenceDao.insertAll(arrayOf(speaker))
        conferenceDao.insertAll(arrayOf(session))

        val sessionSpeaker = SessionSpeaker(sessionId = sessionId, speakerId = speakerId)
        conferenceDao.insertAll(arrayOf(sessionSpeaker))
    }

    private fun getSpeakersBySessionId(sessionId: String): Array<FullSpeaker> {
        val speakersResponse = conferenceDao.getSpeakersBySession(sessionId)
        var speakerResults: Array<FullSpeaker> = arrayOf()
        speakersResponse
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> speakerResults = result }
        testScheduler.triggerActions()
        return speakerResults
    }

    private fun getSessionsById(sessionId: String): Array<FullSession> {
        val response = conferenceDao.getSessions(arrayOf(sessionId))

        var sessionResults: Array<FullSession> = arrayOf()
        response
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> sessionResults = result }
        testScheduler.triggerActions()
        return sessionResults
    }
}