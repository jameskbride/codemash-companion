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
        val session = Session(Id = "1")
        val speaker = Speaker(Id = "1")

        conferenceDao.insertAll(arrayOf(speaker))
        conferenceDao.insertAll(arrayOf(session))

        val sessionSpeaker = SessionSpeaker(sessionId = session.Id, speakerId = speaker.Id)
        conferenceDao.insertAll(arrayOf(sessionSpeaker))

        val response = conferenceDao.getSpeakers(arrayOf(speaker.Id))

        var speakerResults:Array<FullSpeaker> = arrayOf()
        response
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> speakerResults = result }
        testScheduler.triggerActions()

        assertEquals(1, speakerResults.size)
        assertEquals(1, speakerResults[0].sessionSpeakers.size)
    }

    @Test
    fun itCanRetrieveSessionSpeakersFromAFullSpeakerBySessionId() {
        val session = Session(Id = "1")
        val speaker = Speaker(Id = "1")

        conferenceDao.insertAll(arrayOf(speaker))
        conferenceDao.insertAll(arrayOf(session))

        val sessionSpeaker = SessionSpeaker(sessionId = session.Id, speakerId = speaker.Id)
        conferenceDao.insertAll(arrayOf(sessionSpeaker))

        val response = conferenceDao.getSpeakersBySession(sessionId = session.Id)

        var speakerResults:Array<FullSpeaker> = arrayOf()
        response
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> speakerResults = result }
        testScheduler.triggerActions()

        assertEquals(1, speakerResults.size)
        assertEquals(1, speakerResults[0].sessionSpeakers.size)
    }

    @Test
    fun itCanRetrieveSessionSpeakersIndirectlyOnFullSpeakersFromFullSessions() {
        val session = Session(Id = "1")
        val speaker = Speaker(Id = "1")

        conferenceDao.insertAll(arrayOf(speaker))
        conferenceDao.insertAll(arrayOf(session))

        val sessionSpeaker = SessionSpeaker(sessionId = session.Id, speakerId = speaker.Id)
        conferenceDao.insertAll(arrayOf(sessionSpeaker))

        val response = conferenceDao.getSessions(arrayOf(session.Id))

        var sessionResults:Array<FullSession> = arrayOf()
        response
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> sessionResults = result }
        testScheduler.triggerActions()

        assertEquals(1, sessionResults.size)

        val speakersResponse = conferenceDao.getSpeakersBySession(sessionResults[0].sessionSpeakers[0].sessionId)
        var speakerResults:Array<FullSpeaker> = arrayOf()
        speakersResponse
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .subscribe { result -> speakerResults = result }
        testScheduler.triggerActions()

        assertEquals(1, speakerResults.size)
        assertEquals(1, speakerResults[0].sessionSpeakers.size)
    }
}